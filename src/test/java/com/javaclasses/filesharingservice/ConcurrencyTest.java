package com.javaclasses.filesharingservice;


import com.javaclasses.filesharingservice.dao.UserRepository;
import com.javaclasses.filesharingservice.dao.UserRepositoryImpl;
import com.javaclasses.filesharingservice.dao.entities.User;
import com.javaclasses.filesharingservice.services.UserRegistrationService;
import com.javaclasses.filesharingservice.services.customdatatypes.Email;
import com.javaclasses.filesharingservice.services.customdatatypes.FirstName;
import com.javaclasses.filesharingservice.services.customdatatypes.LastName;
import com.javaclasses.filesharingservice.services.customdatatypes.Password;
import com.javaclasses.filesharingservice.services.impl.UserRegistrationServiceImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

public class ConcurrencyTest {

    @Test
    public void testAsynchronousExecution() throws ExecutionException, InterruptedException {

        final UserRepository userRepository = new UserRepositoryImpl();
        final UserRegistrationService userRegistrationService = new UserRegistrationServiceImpl(userRepository);

        final ExecutorService executor = Executors.newFixedThreadPool(50);

        final CountDownLatch startLatch = new CountDownLatch(50);

        final List<Future<User>> results = new ArrayList<>();

        AtomicInteger someDifferenceInEmail = new AtomicInteger(0);

        Callable<User> callable = new Callable<User>() {


            @Override
            public User call() throws Exception {

                startLatch.countDown();
                startLatch.await();

                Email email = new Email("email" + someDifferenceInEmail.getAndIncrement());
                Password password = new Password("password");
                FirstName firstName = new FirstName("firstName");
                LastName lastName = new LastName("lastName");
                userRegistrationService.registerUser(email, password, firstName, lastName);

                return userRepository.findUserByEmail(email);
            }
        };

        for(int i=0; i< 50; i++){


            Future<User> future = executor.submit(callable);

            results.add(future);
        }



        for (Future<User> future : results){
            future.get();
        }

    }
}
