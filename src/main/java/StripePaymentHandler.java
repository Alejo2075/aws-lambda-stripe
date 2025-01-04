package 

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.json.JSONObject;

public class StripePaymentHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

        try {
            // 1. Set Stripe's secret key from environment variables
            Stripe.apiKey = System.getenv("STRIPE_SECRET_KEY");

            // 2. Parse the event body (e.g., {"paymentMethodId":"pm_xxx","amount":1000,"currency":"usd"})
            String body = event.getBody();
            JSONObject requestData = new JSONObject(body);

            String paymentMethodId = requestData.getString("paymentMethodId");
            long amount = requestData.getLong("amount"); // in cents
            String currency = requestData.getString("currency");

            // 3. Create the PaymentIntent in Stripe
            PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder()
                    .setPaymentMethod(paymentMethodId)
                    .setAmount(amount)
                    .setCurrency(currency)
                    .setConfirmationMethod(PaymentIntentCreateParams.ConfirmationMethod.MANUAL)
                    .setConfirm(true)
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(createParams);

            // 4. Return success response
            JSONObject responseBody = new JSONObject();
            responseBody.put("message", "Payment processed successfully");
            responseBody.put("paymentIntent", paymentIntent.toJson());

            response.setStatusCode(200);
            response.setBody(responseBody.toString());
            return response;

        } catch (Exception e) {
            // 5. Error handling
            JSONObject errorBody = new JSONObject();
            errorBody.put("error", e.getMessage());

            response.setStatusCode(400);
            response.setBody(errorBody.toString());
            return response;
        }
    }
}
