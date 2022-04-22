package jafar.mockaroo;

import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class FakerTake {

    private final Faker faker;

    private HashMap<String, Supplier<String>>fakers;

    @PostConstruct
    private void begin(){
        fakers=new HashMap<>();
        fakers.put(Type.ADDRESS.name(),()->"\'"+ faker.address().buildingNumber().concat(faker.address().streetName()).concat(faker.address().streetSuffix())+"\'");
        fakers.put(Type.FIRSTNAME.name(), ()->"\'"+faker.name().firstName()+"\'");
        fakers.put(Type.LASTNAME.name(), ()->"\'"+faker.name().lastName()+"\'");
        fakers.put(Type.USERNAME.name(),()->"\'"+ faker.name().username()+"\'");
        fakers.put(Type.FULLNAME.name(),()->"\'"+faker.name().fullName()+"\'");
        fakers.put(Type.IPADDRESS.name(), ()->"\'"+faker.internet().ipV4Address()+"\'");
        fakers.put(Type.ANCIENT.name(), ()->"\'"+faker.ancient().hero()+"\'");
        fakers.put(Type.CRYPTO.name(), ()->"\'"+faker.crypto().md5()+"\'");
        fakers.put(Type.ANIMAL.name(), ()->"\'"+faker.animal().name()+"\'");
        fakers.put(Type.AVATAR.name(), ()->"\'"+faker.avatar().image()+"\'");
        fakers.put(Type.ARTIST.name(), ()->"\'"+faker.artist().name()+"\'");
        fakers.put(Type.AVIATION.name(), ()->"\'"+faker.aviation().airport()+"\'");
        fakers.put(Type.COMPANY.name(), ()->"\'"+faker.company().name()+"\'");
        fakers.put(Type.BEER.name(), ()->"\'"+faker.beer().name()+"\'");
        fakers.put(Type.BLANK.name(), ()->"\'"+" "+"\'");
        fakers.put(Type.BOOK.name(), ()->"\'"+faker.book().title()+"\'");
        fakers.put(Type.BOOLEAN.name(), ()-> Boolean.toString(faker.bool().bool()));
        fakers.put(Type.EMAIL.name(), ()->"\'"+faker.internet().emailAddress()+"\'");
        fakers.put(Type.CAT.name(), ()->"\'"+faker.cat().name()+"\'");
        fakers.put(Type.COMMERCE.name(), ()->"\'"+faker.commerce().productName()+"\'");
    }

    public Resource getSql(List<FakeDto>dtos,int size){
        File tempFile = new File("/uploads/"+System.currentTimeMillis()+".sql");
        try (FileWriter writer=new FileWriter(tempFile,true);){



            IntStream.range(0,size).parallel().forEach((a)->{
                StringJoiner values=new StringJoiner(",");
                StringJoiner headers=new StringJoiner(",");
                dtos.forEach((b)->{
                    headers.add(b.getType().name().toLowerCase());
                    values.add(fakers.get(b.getType().name()).get());
                });
                try {
                    String s = "insert into mock_data(" + headers + ") values(" + values + ");\n";
                    writer.write(s);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });


            return new FileSystemResource(tempFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }




}
