package alexa.eden.gogenesis.alexa.pubnub;

import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.enums.PNStatusCategory;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

import java.util.Arrays;

public final class  PubNubHandler {
    private static PubNubHandler pubNubHandler;
    private PNConfiguration pnConfiguration;
    private PubNub pubnub;
    private SubscribeCallback subscribeCallback;

    private final String KEY_PUB_NUB_SUSCRIBE = "demo";
    private final String KEY_PUB_NUB_PUBLISH = "demo";

    public void init() {
        configClient();
        addListener();
    }
    public static PubNubHandler getInstance() {
        if(pubNubHandler == null)
            pubNubHandler = new PubNubHandler();
        return pubNubHandler;
    }
    private void configClient() {
        pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey(KEY_PUB_NUB_SUSCRIBE);
        pnConfiguration.setPublishKey(KEY_PUB_NUB_PUBLISH);

        pubnub = new PubNub(pnConfiguration);
    }
    private void addListener() {
        subscribeCallback = new SubscribeCallback() {
            @Override
            public void status(PubNub pubnub, PNStatus status) {
                if (status.getCategory() == PNStatusCategory.PNUnexpectedDisconnectCategory) {
                    // internet got lost, do some magic and call reconnect when ready
                    pubnub.reconnect();
                } else if (status.getCategory() == PNStatusCategory.PNTimeoutCategory) {
                    // do some magic and call reconnect when ready
                    pubnub.reconnect();
                } else {
                    //log.error(status);
                }
            }

            @Override
            public void message(PubNub pubnub, PNMessageResult message) {

            }

            @Override
            public void presence(PubNub pubnub, PNPresenceEventResult presence) {

            }
        };

        pubnub.addListener(subscribeCallback);
    }
    private void removeListener() {
        pubnub.removeListener(subscribeCallback);
    }
    public void suscribe(String channel) {
        pubnub.subscribe()
                .channels(Arrays.asList(channel)) // subscribe to channels
                .execute();
    }
    private void unsuscribe(String channel) {
        pubnub.unsubscribe()
                .channels(Arrays.asList(channel))
                .execute();
    }
    private void destroy() {
        pubnub.destroy();
    }
    public void publish(String channel) {
        pubnub.publish()
                .message(Arrays.asList("hello", "there"))
                .channel(channel)
                .async(new PNCallback<PNPublishResult>() {
                    @Override
                    public void onResponse(PNPublishResult result, PNStatus status) {
                        // handle publish result, status always present, result if successful
                        // status.isError to see if error happened
                    }
                });
    }
}
