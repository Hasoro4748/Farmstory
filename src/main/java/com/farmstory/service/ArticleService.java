package com.farmstory.service;

import com.farmstory.dto.*;
import com.farmstory.entity.Article;
import com.farmstory.entity.CsArticle;
import com.farmstory.repository.ArticleRepository;
import com.farmstory.repository.CateRepository;
import com.farmstory.repository.CsArticleRepository;
import com.farmstory.repository.FileRepository;
import com.querydsl.core.Tuple;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class  ArticleService {


    private final ArticleRepository articleRepository;
    private final CateRepository cateRepository;
    private final ModelMapper modelMapper;
    private final CsArticleRepository csArticleRepository;
    private final FileRepository fileRepository;

    public int updateFileCount(int ano){
        int count = 0;
        count= fileRepository.selectFileCount(ano);

        Optional<Article> article = articleRepository.findById(ano);
        if (article.isPresent()) {
            Article article1 = article.get();
            article1.setFile(count);
            Article updateArticle= articleRepository.save(article1);
            return updateArticle.getArticleNo();
        }else{
            return 0;
        }

    }

    public List<ArticleDTO> mainArticle(int cateNo){
        List<Tuple> tuples= articleRepository.MainViewArticle(cateNo);
        log.info(tuples);
        if(!tuples.isEmpty()){
            List<ArticleDTO> articleDTOS = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            for(Tuple tuple : tuples){
                Integer articleNoInteger = tuple.get(0, Integer.class);                 ArticleDTO articleDTO = ArticleDTO.builder()
                        .articleNo( articleNoInteger != null ? articleNoInteger : 0 )
                        .cateNo(cateNo)
                        .title(tuple.get(1,String.class))
                        .date(tuple.get(2, LocalDateTime.class).format(formatter))
                        .build();
                articleDTOS.add(articleDTO);
            }
            return articleDTOS;
        }
        return null;

    }
    public List<ArticleDTO> mainNoticeArticle(int cateNo){
        List<Tuple> tuples= articleRepository.MainNoticeArticle(cateNo);
        log.info(tuples);
        if(!tuples.isEmpty()){
            List<ArticleDTO> articleDTOS = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            for(Tuple tuple : tuples){
                Integer articleNoInteger = tuple.get(0, Integer.class);                 ArticleDTO articleDTO = ArticleDTO.builder()
                        .articleNo( articleNoInteger != null ? articleNoInteger : 0 )
                        .cateNo(cateNo)
                        .title(tuple.get(1,String.class))
                        .date(tuple.get(2, LocalDateTime.class).format(formatter))
                        .build();
                articleDTOS.add(articleDTO);
            }
            return articleDTOS;
        }
        return null;

    }

    public int insertArticle(ArticleDTO articleDTO, int cateNo, HttpServletRequest req) {
        log.info("article controller :"+articleDTO.toString());

        //첨부파일 객체 가져오기
        List<MultipartFile>  files = articleDTO.getFiles();
        int filesize = 0;
        if(files != null && !files.isEmpty()) {
            filesize =files.size();
        }


        articleDTO.setRegIp(req.getRemoteAddr());
        Article article = modelMapper.map(articleDTO, Article.class);
        article.setFile(filesize);
        Article savedArticle = articleRepository.save(article);

        return savedArticle.getArticleNo();
    }
    public ArticleDTO selectArticle(int articleNo) {
        Optional<Article> opt = articleRepository.findById(articleNo);

        if(opt.isPresent()) {
            Article article = opt.get();
            article.setHit(article.getHit() + 1);
            Article hitupdateArticle = articleRepository.save(article);

            return modelMapper.map(hitupdateArticle, ArticleDTO.class);
        }
        return null;
    }

    public List<ArticleDTO> selectCateArticle(int cateNo){
        List<Article> entitys = articleRepository.findByCateNo(cateNo);

        List<ArticleDTO> articles = entitys.stream().map(article -> article.toDTO()).toList();

        return articles;


    }

    //comment update
    public int updateCommentCount(int parentNo, boolean upanddown){

        int result =0;
        //up ==ture
        Optional<Article> optional = articleRepository.findById(parentNo);
        if(upanddown){
            if (optional.isPresent()){
                Article article = optional.get();
                article.setCom(article.getCom() + 1);
                articleRepository.save(article);
                result=1;
            }
        }else{
            if (optional.isPresent()){
                Article article = optional.get();
                article.setCom(article.getCom() - 1);
                articleRepository.save(article);
                result =-1;
            }
            //down false
        }


        return result;
    }

    public int updateCommentCsCount(String CsparentNo, boolean upanddown){

        int result =0;
        //up ==ture
        Optional<CsArticle> optional = csArticleRepository.findById(CsparentNo);
        if(upanddown){
            if (optional.isPresent()){
                CsArticle article = optional.get();
                article.setComment(article.getComment() + 1);
                csArticleRepository.save(article);
                result=1;
            }
        }else{
            if (optional.isPresent()){
                CsArticle article = optional.get();
                article.setComment(article.getComment() - 1);
                csArticleRepository.save(article);
                result =-1;
            }
            //down false
        }


        return result;
    }


    public List<ArticleDTO> selectArticles() { return null;}
    public int updateArticle(ArticleDTO articleDTO,int cateNo) {


            //첨부파일 객체 가져오기
            List<MultipartFile>  files = articleDTO.getFiles();
            int filesize = 0;
            if(files != null && !files.isEmpty()) {
                filesize =files.size();
            }
            Article article = modelMapper.map(articleDTO, Article.class);
            article.setFile(filesize);
            article.setDate(LocalDateTime.parse(articleDTO.getDate()));
            article.setRegIp(articleDTO.getRegIp());
            article.setCateNo(cateNo);
            article.setUpdateDate(LocalDateTime.now());
            Article savedArticle = articleRepository.save(article);

            return savedArticle.getArticleNo();



    }
    public int deleteArticle(int articleNo) {
        boolean exists =  articleRepository.existsById(articleNo);
        int result=0;
        if(exists) {
            articleRepository.deleteById(articleNo);
            result = 1;
        }
        return result;
    }


    //공지사항 가져오기
    public List<ArticleDTO> selectNotice(int cateNo){
       List<Tuple> articles = articleRepository.selectNotice(cateNo);
       log.info(articles);
        List<ArticleDTO> articleList = articles.stream().map(tuple ->{
                    Article article = tuple.get(0,Article.class);
                    String nick=tuple.get(1,String.class);
                    article.setNick(nick);

                    ArticleDTO articleDTO = modelMapper.map(article,ArticleDTO.class);
                    articleDTO.getSubStringRdate();
                    return  articleDTO;
                }
        ).toList();



        return articleList;
    }

    public CSPageResponseDTO selectCsArticleForUser(CSPageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable("csNo",10);
        Page<Tuple> pageCsArticle = null;

            pageCsArticle =csArticleRepository.selectCsForUser(requestDTO,pageable,requestDTO.getUid());

            List<CsArticleDTO> csArticleList= pageCsArticle.stream().map(tuple -> {
                CsArticle csArticle = tuple.get(0,CsArticle.class);
                String nick = tuple.get(1,String.class);
                csArticle.setNick(nick);
                return modelMapper.map(csArticle,CsArticleDTO.class);
            }).toList();

        int total = (int)pageCsArticle.getTotalElements();

        return CSPageResponseDTO.builder()
                .csArticleDTOS(csArticleList)
                .total(total)
                .cspageRequestDTO(requestDTO)
                .build();

    }


    public PageResponseDTO getAllArticles(PageRequestDTO pageRequestDTO,int cateNo) {

        Pageable pageable = pageRequestDTO.getPageable("articleNo",10);
        Page<Tuple> pageArticle =null;
        if(pageRequestDTO.getKeyword()==null) {

                pageArticle = articleRepository.selectArticleAllForList(pageRequestDTO, pageable, cateNo);


        }else{
            pageArticle = articleRepository.selectArticleForSearch(pageRequestDTO, pageable,cateNo);
        }

        List<ArticleDTO> articleList = pageArticle.stream().map(tuple ->{
                    Article article = tuple.get(0,Article.class);
                    String nick=tuple.get(1,String.class);
                    Long com =tuple.get(2,Long.class);
                    int commentCount = com.intValue();  // Convert Long to int if needed
                    article.setCom(commentCount);
                    article.setNick(nick);

                    ArticleDTO articleDTO = modelMapper.map(article,ArticleDTO.class);
                    articleDTO.getSubStringRdate();
                    return  articleDTO;
                }
        ).toList();


        int total = (int)pageArticle.getTotalElements();

        log.info("total : "+total);
        return PageResponseDTO.builder()
                .dtoList(articleList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();


    }


    public CSPageResponseDTO selectCSByAdmin(CSPageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable("csNo",10);
        Page<Tuple> pageArticle =null;
        if(pageRequestDTO.getKeyword()==null) {
            pageArticle = csArticleRepository.selectCsForAdmin(pageRequestDTO, pageable,504);


        }else{
            pageArticle = csArticleRepository.selectCsSearchForAdmin(pageRequestDTO, pageable, 504);

        }

        List<CsArticleDTO> articleList = pageArticle.stream().map(tuple ->{
                    CsArticle article = tuple.get(0,CsArticle.class);
                    String nick=tuple.get(1,String.class);
                    article.setNick(nick);
                    return modelMapper.map(article, CsArticleDTO.class);
                }
        ).toList();


        int total = (int)pageArticle.getTotalElements();

        log.info("total : "+total);
        return CSPageResponseDTO.builder()
                .csArticleDTOS(articleList)
                .total(total)
                .cspageRequestDTO(pageRequestDTO)
                .build();


    }

    public String insertCsArticle(CsArticleDTO csArticleDTO,HttpServletRequest req) {
        log.info("article controller :"+csArticleDTO.toString());

        //첨부파일 객체 가져오기
        List<MultipartFile>  files = csArticleDTO.getFiles();
        int filesize = 0;
        if(files != null && !files.isEmpty()) {
            filesize =files.size();
        }


        int no = csArticleRepository.findAll().size();

        csArticleDTO.setRegIp(req.getRemoteAddr());
        log.info("article controller2 :"+csArticleDTO.toString());

        CsArticle article = modelMapper.map(csArticleDTO, CsArticle.class);

        log.info("CsArticle : "+article);
        article.setFile(filesize);
        article.setCsNo("cs"+(no+1));
        CsArticle savedArticle = csArticleRepository.save(article);

        return savedArticle.getCsNo();

    }

    public CsArticleDTO selectCsArticle(String csNo) {
        Optional<CsArticle> opt = csArticleRepository.findById(csNo);

        if(opt.isPresent()) {
            CsArticle article = opt.get();
            article.setHit(article.getHit() + 1);
            CsArticle hitupdateArticle = csArticleRepository.save(article);
            return modelMapper.map(hitupdateArticle, CsArticleDTO.class);
        }
        return null;
    }



    public String updateCsCompleted(String csNo){
        Optional<CsArticle> opt = csArticleRepository.findById(csNo);
        if(opt.isPresent()) {
            CsArticle article = opt.get();
            article.setCompleted(true);
            article.setUpdateDate(LocalDateTime.now());
            CsArticle savedArticle = csArticleRepository.save(article);

            return savedArticle.getCsNo();
        }
        return null;
    }






}