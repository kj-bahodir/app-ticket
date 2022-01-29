package ai.ecma.appticket.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {
    @Value(value = "${twilio.accountSID}")
    private String accountSID;

    @Value(value = "${twilio.authToken}")
    private String authToken;

    @Value(value = "${twilio.myTwilioPhoneNumber}")
    private String myTwilioPhoneNumber;

    public boolean sendVerificationCode(String code, String receiverPhoneNumber) {
        try {
            Twilio.init(accountSID, authToken);
            Message message = Message.creator(
                    new PhoneNumber(receiverPhoneNumber),
                    new PhoneNumber(myTwilioPhoneNumber),
                    "Your verification code: " + code + " \nBuni hechkimga bermang!"
            ).create();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
