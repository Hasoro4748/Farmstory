package com.farmstory.controller.article;


import com.farmstory.dto.CateDTO;
import com.farmstory.dto.CommentDTO;
import com.farmstory.service.ArticleService;
import com.farmstory.service.CategoryService;
import com.farmstory.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Log4j2
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CategoryService categoryService;
    private final ArticleService articleService;


    @PostMapping("/comment/write")
    public ResponseEntity<CommentDTO> write(@RequestBody CommentDTO commentDTO,

                                            HttpServletRequest req) {

        String regip = req.getRemoteAddr();
        commentDTO.setRegip(regip);
        log.info(commentDTO);

        CommentDTO dto = commentService.insertComment(commentDTO);
        log.info("dto : "+dto);
        return ResponseEntity.ok().body(dto);

    }

    @ResponseBody
    @GetMapping("/comment/delete")
    public ResponseEntity<?> delete(@RequestParam int no, @RequestParam int pg, @RequestParam("writer") String writer) {


        int result=  commentService.deleteComment(no);
        return ResponseEntity.ok().body(result);
//     if(parentNo > 0 ){
//         return "redirect:/article/"+cate.getCateGroup()+"/"+cate.getCateName()+"/"+parentNo+"?content=view&pg="+pg;
//
//     }
//        return "redirect:/article/"+cate.getCateGroup()+"/"+cate.getCateName()+"/"+parentNo+"?content=view&success=false&pg="+pg;

    }

    @ResponseBody
    @PostMapping("/comment/modify")
    public ResponseEntity<CommentDTO> modify(@RequestParam("no") int no,
                          @RequestParam("comment") String comment, HttpServletRequest req) {

        CommentDTO commentDTO = commentService.selectComment(no);
        log.info(commentDTO);

        commentDTO.setContent(comment);

       CommentDTO updatedComment =  commentService.updateComment(commentDTO);

        return ResponseEntity.ok().body(updatedComment);

    }

}
