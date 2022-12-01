package will.peterson.slackconsole;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class SlackControllerTest {

    @Test
    void outputMessagesTest() {

        SlackController slackController = new SlackController();
        ItemList memberList = slackController.getOutputMessages(10);

    }

    @Test
    void relatedNamesTest() throws IOException {

        SlackController slackController = new SlackController();
        slackController.postRelatedNames("will");

    }

}
