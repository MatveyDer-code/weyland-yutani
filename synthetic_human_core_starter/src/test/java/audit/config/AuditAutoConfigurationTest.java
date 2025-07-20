package audit.config;

import audit.AuditMode;
import audit.AuditSender;
import audit.impl.ConsoleAuditSender;
import audit.impl.KafkaAuditSender;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class AuditAutoConfigurationTest {
     private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
             .withConfiguration(AutoConfigurations.of(AuditAutoConfiguration.class));

     @Test
     void shouldUseConsoleAuditSenderWhenModeIsConsole() {
         contextRunner.withPropertyValues("audit.mode=CONSOLE")
                 .run(context -> {
                     assertThat(context).hasSingleBean(AuditSender.class);
                     assertThat(context.getBean(AuditSender.class)).isInstanceOf(ConsoleAuditSender.class);
                 });
     }

     @Test
     void shouldUseKafkaAuditSenderWhenModeIsKafka() {
         contextRunner.withPropertyValues("audit.mode=KAFKA")
                 .run(context -> {
                     assertThat(context).hasSingleBean(AuditSender.class);
                     assertThat(context.getBean(AuditSender.class)).isInstanceOf(KafkaAuditSender.class);
                 });
     }


    @Test
    void whenAuditModeNotSet_thenConsoleAuditSenderIsCreatedByDefault() {
        contextRunner
                .run(context -> {
                    assertThat(context).hasSingleBean(AuditProperties.class);
                    assertThat(context).hasSingleBean(ConsoleAuditSender.class);
                    AuditProperties props = context.getBean(AuditProperties.class);
                    assertThat(props.getMode()).isEqualTo(AuditMode.CONSOLE);
                });
    }
}