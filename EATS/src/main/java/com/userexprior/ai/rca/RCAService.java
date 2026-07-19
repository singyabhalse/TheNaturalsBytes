package com.userexprior.ai.rca;


import org.springframework.ai.chat.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RCAService {

    @Autowired
    private ChatClient chatClient;

    public String analyzeLogs() {
        String logs = "2026-07-18 14:30:01 INFO  [http-nio-8082-exec-1] com.userexprior.controller.OrderController - Order request received OrderId=ORD30001 CustomerId=CUST101\n" +
                "\n" +
                "2026-07-18 14:30:02 INFO  [http-nio-8082-exec-1] com.userexprior.service.OrderService - Validating customer\n" +
                "\n" +
                "2026-07-18 14:30:03 INFO  [http-nio-8082-exec-1] com.userexprior.service.InventoryService - Inventory available ProductId=P100 Qty=2\n" +
                "\n" +
                "2026-07-18 14:30:04 INFO  [http-nio-8082-exec-1] com.userexprior.service.CouponService - Coupon applied CouponCode=SAVE10\n" +
                "\n" +
                "2026-07-18 14:30:05 INFO  [http-nio-8082-exec-1] com.userexprior.service.PaymentService - Initiating payment Amount=2500\n" +
                "\n" +
                "2026-07-18 14:30:06 INFO  [http-nio-8082-exec-1] com.userexprior.client.PaymentGatewayClient - Calling external payment gateway\n" +
                "\n" +
                "2026-07-18 14:30:16 WARN  [http-nio-8082-exec-1] com.userexprior.client.PaymentGatewayClient - Payment gateway response delayed\n" +
                "\n" +
                "2026-07-18 14:30:21 ERROR [http-nio-8082-exec-1] com.userexprior.client.PaymentGatewayClient - Payment Gateway Timeout\n" +
                "\n" +
                "java.net.SocketTimeoutException: Read timed out\n" +
                "\n" +
                "    at java.base/sun.nio.ch.NioSocketImpl.timedRead(NioSocketImpl.java:278)\n" +
                "    at java.base/sun.nio.ch.NioSocketImpl.implRead(NioSocketImpl.java:304)\n" +
                "    at java.base/java.net.Socket$SocketInputStream.read(Socket.java:966)\n" +
                "    at org.springframework.web.client.RestTemplate.doExecute(RestTemplate.java:789)\n" +
                "    at com.userexprior.client.PaymentGatewayClient.processPayment(PaymentGatewayClient.java:64)\n" +
                "\n" +
                "2026-07-18 14:30:22 ERROR [http-nio-8082-exec-1] com.userexprior.service.PaymentService - Payment processing failed OrderId=ORD30001\n" +
                "\n" +
                "org.springframework.web.client.ResourceAccessException:\n" +
                "I/O error on POST request\n" +
                "\n" +
                "    at com.userexprior.service.PaymentService.process(PaymentService.java:88)\n" +
                "\n" +
                "2026-07-18 14:30:23 INFO  [http-nio-8082-exec-1] com.userexprior.service.OrderService - Rolling back transaction\n" +
                "\n" +
                "2026-07-18 14:30:24 WARN  [http-nio-8082-exec-1] com.userexprior.service.InventoryService - Releasing reserved inventory\n" +
                "\n" +
                "2026-07-18 14:30:25 INFO  [http-nio-8082-exec-1] com.userexprior.service.InventoryService - Inventory released successfully\n" +
                "\n" +
                "2026-07-18 14:30:26 INFO  [http-nio-8082-exec-1] com.userexprior.kafka.NotificationProducer - Publishing payment failure event\n" +
                "\n" +
                "2026-07-18 14:30:27 INFO  [http-nio-8082-exec-1] com.userexprior.kafka.NotificationProducer - Event published successfully\n" +
                "\n" +
                "2026-07-18 14:30:28 INFO  [http-nio-8082-exec-1] com.userexprior.service.EmailService - Sending payment failure email\n" +
                "\n" +
                "2026-07-18 14:30:29 INFO  [http-nio-8082-exec-1] com.userexprior.service.EmailService - Email sent successfully\n" +
                "\n" +
                "2026-07-18 14:30:30 ERROR [http-nio-8082-exec-1] com.userexprior.controller.OrderController - Order failed OrderId=ORD30001\n" +
                "\n" +
                "2026-07-18 14:30:31 INFO  [http-nio-8082-exec-1] com.userexprior.controller.HealthController - Health check completed";

        String prompt = """
            Analyze these production logs.
            
            Provide:
            
            1. Root Cause
            2. Business Impact
            3. Severity
            4. Issue on which line number
            5. Primary Exception:
            6. Affected Service(s):
            7. Issue Timeline:
            8. Recommended Fix
            9. Add Code for Fix issue
            10. Till where call has Successfully completed.
            
            
            Logs:
            
            %s
            """.formatted(logs);

        return chatClient.call(prompt);
    }


}
