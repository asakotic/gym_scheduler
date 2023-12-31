package raf.microservice.components.notificationservice.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import raf.microservice.components.notificationservice.model.Notification;
import raf.microservice.components.notificationservice.model.Type;
import raf.microservice.components.notificationservice.repository.NotificationRepository;
import raf.microservice.components.notificationservice.repository.TypeRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

@Profile({"default"})
@Component
public class DataRunner implements CommandLineRunner {

    private final TypeRepository typeRepository;
    private final NotificationRepository notificationRepository;

    public DataRunner(TypeRepository typeRepository, NotificationRepository notificationRepository){
        this.typeRepository = typeRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Type type = new Type();
        type.setName("REGISTER_USER");
        type.setFormat("Hello %name% %lastname% ! To verify your account please open following link %link%");
        typeRepository.save(type);

        Notification notification = new Notification();
        notification.setMessage("Poruka");
        notification.setMailReceiver("karisik.mehmedalija@gmail.com");
        notification.setType(type);
        notification.setUsername("karisikm");
        notification.setDateSent(LocalDateTime.of(2023, Month.DECEMBER, 31, 20, 5, 55));
        notificationRepository.save(notification);

        Notification notification1 = new Notification();
        notification1.setMessage("Poruka2232");
        notification1.setMailReceiver("karisik.mehmedalija@gmail.com");
        notification1.setType(type);
        notification1.setUsername("karisikm");
        notification1.setDateSent(LocalDateTime.of(2023, Month.DECEMBER, 30, 14, 33, 22));
        notificationRepository.save(notification1);

        Notification notification2 = new Notification();
        notification2.setMessage("Poruka2232");
        notification2.setMailReceiver("karisik.mehmedalija@gmail.com");
        notification2.setType(type);
        notification2.setUsername("mking03321122");
        notification2.setDateSent(LocalDateTime.of(2023, Month.DECEMBER, 29, 14, 33, 11));
        notificationRepository.save(notification2);


    }
}