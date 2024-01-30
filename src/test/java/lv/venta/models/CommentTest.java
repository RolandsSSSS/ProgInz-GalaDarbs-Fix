package lv.venta.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommentTest {

	@Test
	void test() {
        long id = 0;
        String apraksts = "Test koments";
        LocalDateTime datums = LocalDateTime.now();
        Thesis galadarbs = new Thesis();

        Comment comment = new Comment();
        
        comment.setDescription(apraksts);
        comment.setCommentDate(datums);
        comment.setThesis(galadarbs);

        assertEquals(id, comment.getIdco());
        assertEquals(apraksts, comment.getDescription());
        assertEquals(datums, comment.getCommentDate());
        assertEquals(galadarbs, comment.getThesis());
	}

}
