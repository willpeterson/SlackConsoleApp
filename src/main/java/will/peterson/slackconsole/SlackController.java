package will.peterson.slackconsole;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@RestController
public class SlackController {

    static Logger logger = Logger.getLogger(SlackController.class.getName());

    private String apiToken = "xoxb-4442054110403-4435903376998-IFXvgbsaug8Esd5eja6ox29v";
    private String webhookToken = "T04D01L38BV/B04DQ5ZHY56/yJdZQRwMbGFzTAaIjeZ0ySPn";
    /**
     * Select a random user from the available users in the workspace, then
     *   chooses a random channel from the channels the user is a part of
     *   displays up to 10 messages from that channel (it will display fewer if there are fewer available)
     * @param messageLimit specify message limit, defaults to 10
     * @return
     */
    @GetMapping("outputMessages")
    public ItemList getOutputMessages(@RequestParam(name="messageLimit",required=false) final Integer messageLimit) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + apiToken);

        // Select a random user from the available users in the workspace
        ResponseEntity<ItemList> members = restTemplate.exchange(
                "https://slack.com/api/users.list",
                HttpMethod.GET,
                new HttpEntity<>("parameters", headers),
                ItemList.class
        );

        Random random = new Random();
        int membersSize = members.getBody().getItems().size();
        int randomUserInt = random.ints(0, membersSize).findFirst().getAsInt();
        String randomUserId = members.getBody().getItems().get(randomUserInt).getID();
        logger.info("Random user selected: " + randomUserId);

        // Then chooses a random channel from the channels the user is a part of
        ResponseEntity<ItemList> conversations = restTemplate.exchange(
                "https://slack.com/api/users.conversations?user=" + randomUserId,
                HttpMethod.GET,
                new HttpEntity<>("parameters", headers),
                ItemList.class
        );
        int conversationsSize = conversations.getBody().getItems().size();
        if (conversationsSize == 0) {
            logger.warning("No conversations");
            return new ItemList();
        }
        int randomChannelInt = random.ints(0, conversationsSize).findFirst().getAsInt();
        String randomChannelId = conversations.getBody().getItems().get(randomChannelInt).getID();
        logger.info("Random channel selected: " + randomChannelId);

        // displays up to 10 messages from that channel (it will display fewer if there are fewer available)
        int limit = (messageLimit == null) ? 10 : messageLimit;
        ResponseEntity<ItemList> conversationHistories = restTemplate.exchange(
                "https://slack.com/api/conversations.history?channel=" + randomChannelId + "&limit=" + limit,
                HttpMethod.GET,
                new HttpEntity<>("parameters", headers),
                ItemList.class
        );
        for (Item item : conversationHistories.getBody().getItems()
             ) {
            logger.info("Message found in channel: " + item.getText());
        }
        return  conversationHistories.getBody();
    }

    /**
     * When given a single name, uses the Incoming Webhooks feature of Slack to post related names
     * @param name to check for related names
     * @return
     */
    @GetMapping("postRelatedNames/{name}")
    public HttpStatus postRelatedNames(@PathVariable("name") final String name) throws IOException {

        RestTemplate restTemplate = new RestTemplate();

        RelatedNames relatedNames = restTemplate.getForObject(
                "https://www.behindthename.com/api/related.json?key=bo510933951&name=" + name,
                RelatedNames.class
        );
        String relatedNamesJson = toJson(relatedNames.getNames());
        logger.info("found related names: " + relatedNamesJson);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> services = restTemplate.exchange(
                "https://hooks.slack.com/services/" + webhookToken,
                HttpMethod.POST,
                new HttpEntity<>(relatedNamesJson, headers),
                String.class
        );
        return HttpStatus.OK;
    }

    private String toJson(List<String> input) {
        StringBuilder sb = new StringBuilder("{\"text\": \"Related names are: ");
        for(int i = 0; i < input.size(); i++) {
            sb.append(input.get(i));
            if(i < input.size()-1) {
                sb.append(", ");
            }
        }
        sb.append("\"}").toString();
        return sb.toString();
    }


}
