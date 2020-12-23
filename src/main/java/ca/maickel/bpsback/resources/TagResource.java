package ca.maickel.bpsback.resources;

import ca.maickel.bpsback.domain.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/tags")
public class TagResource {

    @RequestMapping(method= RequestMethod.GET)
    public List<Tag> list(){
        Tag tag1 = new Tag(1, "banana");
        Tag tag2 = new Tag(2, "fruit");

        List<Tag> list = new ArrayList<Tag>();
        list.add(tag1);
        list.add(tag2);

        return list;
    }
}
