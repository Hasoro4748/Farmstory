package com.farmstory.controller.article;

import com.farmstory.dto.*;
import com.farmstory.repository.CsArticleRepository;
import com.farmstory.security.MyUserDetails;
import com.farmstory.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final EmailService emailService;
    private final FileService fileService;
    private final CsArticleRepository csArticleRepository;


    //
    @GetMapping("/{cateGroup}/{cateName}")
    public String list(@PathVariable("cateGroup") String group,
                           @PathVariable("cateName") String cateName,
                           @RequestParam(value = "content", required = false) String content,
                           @RequestParam(value ="pg", defaultValue = "0") int pg,
                           @RequestParam(value="keyword",defaultValue = "",required = false) String keyword,
                           @RequestParam(value="type",defaultValue = "title",required = false) String type,
                           PageRequestDTO pageRequestDTO,
                           Model model){
        log.info("keyword : "+keyword);
        log.info("type : "+type);
        log.info("pageReqestDTO"+pageRequestDTO);
        log.info("cateGroup "+group+"cateName "+cateName);
        CateDTO cate = categoryService.selectCategory(group,cateName);

        log.info(cate);
        int cateNo = cate.getCateNo();
        pageRequestDTO.setCateNo(cateNo);
        log.info("cate : "+cate);
        //300번대 = croptalk ,  500대 = community
        List<ArticleDTO> articles = null;
        if ((cateNo >= 300 && cateNo < 400) || (cateNo >= 500 && cateNo < 600)) {

            articleService.selectNotice(cateNo);
            PageResponseDTO pageResponseDTO = articleService.getAllArticles(pageRequestDTO,cateNo);

            List<ArticleDTO> notices = articleService.selectNotice(cateNo);
            log.info("start : "+pageResponseDTO.getStart());
            log.info("end : " +pageResponseDTO.getEnd());

            log.info(pageResponseDTO.getDtoList());
            log.info("pageResponseDTO : "+pageResponseDTO);
            model.addAttribute("notices", notices);
            model.addAttribute("pageResponseDTO",pageResponseDTO);


        }

        model.addAttribute("cate", cate);
        model.addAttribute("content", content);

        return "boardIndex";

    }


    //article view
    @GetMapping("/{cateGroup}/{cateName}/{articleNo}")
    public String view(@PathVariable("cateGroup") String group,
                       @PathVariable("cateName") String cateName,
                       @PathVariable("articleNo") int articleNo,
                       @RequestParam(value = "pg",defaultValue = "1") int pg,
                       @RequestParam(value = "content", required = false) String content,
                       Model model){

        log.info("pg : "+pg);

        CateDTO cate = categoryService.selectCategory(group,cateName);




        ArticleDTO articleDTO =  articleService.selectArticle(articleNo);
        log.info("articleDTO : "+articleDTO);
        log.info("content: "+content);
        model.addAttribute("pg",pg);
        model.addAttribute("cate", cate);
        model.addAttribute("content", content);
        model.addAttribute("article", articleDTO);
        return "boardIndex";
    }


    @PostMapping("/{cateNo}/writer")
    public String writer(@PathVariable("cateNo") int cateNo,ArticleDTO articleDTO,  @RequestParam("writer") String writer, HttpServletRequest req){
        log.info("noticeCate : "+articleDTO.getNoticeCate());

        CateDTO cate = categoryService.selectCateNo(cateNo);

        articleDTO.setCateNo(cate.getCateNo());
        articleDTO.setRegIp(req.getRemoteAddr());

        //파일 업로드
        List<FileDTO> uploadFiles = new ArrayList<>();
        if (articleDTO.getFiles() != null && !articleDTO.getFiles().isEmpty()) {
            uploadFiles = fileService.uploadFile(articleDTO); // Process file uploads
        }
        int ano;
        if(cate.getCateNo()==501 || articleDTO.getNoticeCate() != 0){
            articleDTO.setNotice(true);
            ano =articleService.insertArticle(articleDTO,cateNo,req);
        }else {
            ano = articleService.insertArticle(articleDTO, cateNo, req);
        }
        //글 저장
        //파일 저장
        if(!uploadFiles.isEmpty()){
            for(FileDTO fileDTO : uploadFiles){
                fileDTO.setAno(ano);
                int fno = fileService.insertFile(fileDTO);
            }
        }



        if(ano>0){
            return "redirect:/article/"+cate.getCateGroup()+"/"+cate.getCateName()+"?content=list";
        }
        return "redirect:/article/"+cate.getCateGroup()+"/"+cate.getCateName()+"?content=write&success=300";

    }

    @GetMapping("/modify/{cateNo}/{articleNo}")
    public String modifty(@PathVariable("cateNo") int cateNO,
                          @PathVariable("articleNo") int articleNo, @RequestParam("pg") int pg,
                          Model model){
        String content = "modify";
        CateDTO cate = categoryService.selectCateNo(cateNO);


        ArticleDTO articleDTO = articleService.selectArticle(articleNo);

        model.addAttribute("cate", cate);
        model.addAttribute("pg",pg);
        model.addAttribute("article", articleDTO);
        model.addAttribute("content", content);

        return "boardIndex";

    }


    @PostMapping("/modify/{cateNo}/{articleNo}")
    public String modify(@PathVariable("cateNo") int cateNo,
                         @PathVariable("articleNo") int articleNo,
                         @RequestParam("pg") int pg,
                         @RequestParam(value="deleteFiles",defaultValue = "") List<Long> deleteFiles,
                         @RequestParam("files") List<MultipartFile> newfiles,
                         ArticleDTO articleDTO, Model model){

        CateDTO cate = categoryService.selectCateNo(cateNo);
        log.info(articleDTO);

        int result=0;
        if(deleteFiles != null && !deleteFiles.isEmpty()){
            for(Long fno : deleteFiles){
                int fNo = Math.toIntExact(fno);
                result = fileService.deleteFile(fNo);
                articleDTO.setFile(articleDTO.getFile()-1);
            }
        }
        List<FileDTO> uploadFiles = new ArrayList<>();
        if (newfiles != null && !newfiles.isEmpty()) {
            articleDTO.setFiles(newfiles);
            uploadFiles = fileService.uploadFile(articleDTO);
        }
        log.info("file 갯수 : " +articleDTO.getFile());
       int ano =  articleService.updateArticle(articleDTO,cateNo);
        //글 저장
        //파일 저장
        if(!uploadFiles.isEmpty()){
            for(FileDTO fileDTO : uploadFiles){
                fileDTO.setAno(ano);
                int fno = fileService.insertFile(fileDTO);
            }
        }

        //파일 갯수 조정
        articleService.updateFileCount(ano);

        return "redirect:/article/"+cate.getCateGroup()+"/"+cate.getCateName()+"/"+articleNo+"?content=view&pg="+pg;
    }

    @GetMapping("/delete/{cateNo}/{articleNo}")
    public String delete(@PathVariable("articleNo") int articleNo,
                         @PathVariable("cateNo") int cateNo,
                         @RequestParam("pg") int pg, Model model){

        CateDTO cate = categoryService.selectCateNo(cateNo);
        int result = articleService.deleteArticle(articleNo);
        if(result==1){
            return "redirect:/article/"+cate.getCateGroup()+"/"+cate.getCateName()+"?content=list";
        }

        return "redirect:/article/"+cate.getCateGroup()+"/"+cate.getCateName()+"/"+articleNo+"?content=view&pg="+pg;
    }




    //================================================================== CS =====================================================
    //cs List
    @GetMapping("/{uid}/community/cs")
    public String communityCsList(@PathVariable("uid") String uid, @RequestParam("content") String content,@RequestParam(value="pg",defaultValue = "1") int pg,CSPageRequestDTO cspageRequestDTO, Model model){
        CateDTO  cate = categoryService.selectCateNo(504);

        cspageRequestDTO.setUid(uid);
        cspageRequestDTO.setCateNo(504);

        CSPageResponseDTO pageResponseDTO = articleService.selectCsArticleForUser(cspageRequestDTO);

        log.info(pageResponseDTO.getDtoList().size());
        log.info(pageResponseDTO);
        log.info("uidddddddddddddddddd"+pageResponseDTO.getUid());

        model.addAttribute("pageResponseDTO",pageResponseDTO);
        model.addAttribute("pg", pg);
        model.addAttribute("content", content);
        model.addAttribute("cate",cate);
        log.info("content : "+ content);
        log.info(content);
        return "boardIndex";
    }



    //1:1 고객문의cswrite page
    @PostMapping("/{uid}/cs/writer")
    public String communityCsWrite(@PathVariable("uid") String uid,
                                   CsArticleDTO csArticleDTO,
                                   HttpServletRequest req,
                                   Model model){

        CateDTO cate = categoryService.selectCateNo(504);
        log.info("csArticleDTO : "+csArticleDTO);

        csArticleDTO.setCateNo(504);
        csArticleDTO.setWriter(uid);
        csArticleDTO.setRegIp(req.getRemoteAddr());

        //파일 업로드
        List<FileDTO> uploadFiles = new ArrayList<>();
        if (csArticleDTO.getFiles() != null && !csArticleDTO.getFiles().isEmpty()) {
            uploadFiles = fileService.uploadFile(csArticleDTO); // Process file uploads
        }
        csArticleDTO.setFile(uploadFiles.size());
        String csNo = articleService.insertCsArticle(csArticleDTO,req);

        log.info(csNo);
        //글 저장
        //파일 저장
        if(!uploadFiles.isEmpty()){
            for(FileDTO fileDTO : uploadFiles){
                fileDTO.setCsNo(csNo);
                int fno = fileService.insertFile(fileDTO);
            }
        }

        String content="cslist";
        model.addAttribute("cate",cate);
        model.addAttribute("content",content);

        return "redirect:/article/"+uid+"/community/cs?content=cslist";
    }


    // CS View
    @GetMapping("/504/{csNo}")
    public String csView(@PathVariable("csNo") String csNo,
                         @RequestParam("content") String content,
                         @RequestParam(value="pg" ,defaultValue = "0") int pg, Model model){


        CateDTO cate = categoryService.selectCateNo(504);

        CsArticleDTO articleDTO =  articleService.selectCsArticle(csNo);
        log.info(articleDTO.isCompleted());

        model.addAttribute("pg",pg);
        model.addAttribute("cate", cate);
        model.addAttribute("content", content);
        model.addAttribute("article", articleDTO);

        return  "boardIndex";
    }


    //admin 부분 CS list
    @GetMapping("/admin/community/cs")
    public String adminCs( @RequestParam(value="content",defaultValue = "cslist") String content,CSPageRequestDTO pageRequestDTO,Model model){
        CateDTO  cate = categoryService.selectCateNo(504);

        pageRequestDTO.setCateNo(504);

        CSPageResponseDTO cspageResponseDTO = articleService.selectCSByAdmin(pageRequestDTO);


        log.info(cspageResponseDTO.getUid());
        model.addAttribute("pageResponseDTO",cspageResponseDTO);
        model.addAttribute("cate",cate);
        model.addAttribute("content", content);
        return  "boardIndex";
    }






    @GetMapping("/cs/completed/{csNo}")
    public String completed(@PathVariable("csNo") String csNo, @AuthenticationPrincipal MyUserDetails userDetails){

        boolean isAdmin = false;
        String uid = userDetails.getUser().getUid() ;
        if(userDetails != null){
            isAdmin = userDetails.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        }
        String updateCsCompleted = articleService.updateCsCompleted(csNo);

        if(updateCsCompleted != null){
            if(isAdmin){
                return "redirect:/article/admin/community/cs";
            }else{
                return "redirect:/article/"+uid+"/community/cs?content=cslist";
            }

        }
        return "redirect:/article/504/"+csNo+"?content=csview";

    }






}