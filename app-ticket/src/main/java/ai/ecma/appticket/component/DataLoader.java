package ai.ecma.appticket.component;

import ai.ecma.appticket.entity.PayType;
import ai.ecma.appticket.entity.Role;
import ai.ecma.appticket.entity.User;
import ai.ecma.appticket.enums.PayTypeEnum;
import ai.ecma.appticket.enums.PermissionEnum;
import ai.ecma.appticket.repository.PayTypeRepository;
import ai.ecma.appticket.repository.RoleRepository;
import ai.ecma.appticket.repository.UserRepository;
import ai.ecma.appticket.utils.AppConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

@Component
public class DataLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PayTypeRepository payTypeRepository;

    public DataLoader(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, PayTypeRepository payTypeRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.payTypeRepository = payTypeRepository;
    }

    @Value("${dataLoaderMode}")
    private String dataLoaderMode;


    @Override
    public void run(String... args) throws Exception {
//        Role payme = roleRepository.save(new Role(
//                AppConstant.PAYME,
//                "bu payme",
//                new HashSet<>(Arrays.asList(PermissionEnum.values())))
//        );
//        PayType payType = payTypeRepository.save(new PayType(
//                AppConstant.PAYME,
//                PayTypeEnum.CARD
//        ));
//
//        User paymeUser = userRepository.save(new User(
//                AppConstant.PAYME,
//                AppConstant.PAYME,
//                "1234",
//                passwordEncoder.encode("123"),
//                roleRepository.getById(UUID.fromString("f8da4899-a9c5-4e31-872c-38191dd07265")),
//                null,
//                true,
//                payTypeRepository.getById(UUID.fromString("14a25d31-8d10-400a-9729-8d5407c1cc82"))
//        ));


        if (dataLoaderMode.equals("always")) {
            Role admin = roleRepository.save(new Role(
                    AppConstant.ADMIN,
                    "bu admin",
                    new HashSet<>(Arrays.asList(PermissionEnum.values()))));

            roleRepository.save(new Role(
                    AppConstant.USER,
                    "bu client",
                    new HashSet<>()));

            userRepository.save(new User(
                    "admin",
                    "adminov",
                    "123",
                    passwordEncoder.encode("admin123"),
                    admin,
                    null,
                    true));
        }
    }
}
