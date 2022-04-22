package jafar.mockaroo;


import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Control {
    private final FakerTake take;

    @RequestMapping(value = "/get",method = RequestMethod.POST)
    private Resource get(@RequestBody Comes comes){
        System.out.println(comes.getSize());
        switch (comes.getType().name()){
            case "SQL"->{

                return take.getSql(comes.getDtos(),comes.getSize());
            }

        }
        return null;
    }

}
