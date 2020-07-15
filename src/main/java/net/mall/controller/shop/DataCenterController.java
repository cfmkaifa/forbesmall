package net.mall.controller.shop;

import net.mall.Page;
import net.mall.Pageable;
import net.mall.entity.Article;
import net.mall.entity.Business;
import net.mall.security.CurrentStore;
import net.mall.security.CurrentUser;
import net.mall.service.ArticleCategoryService;
import net.mall.service.ArticleService;
import net.mall.util.ConvertUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@Controller("dataCenterController")
@RequestMapping("/datacenter")
public class DataCenterController extends BaseController{
    /**
     * 每页记录数
     */
    private static final int PAGE_SIZE = 20;

    @Inject
    private ArticleCategoryService articleCategoryService;

    @Inject
    private ArticleService articleService;


    /***
     *查询视频
     * @param
     * @param model
     * @return
     */
    @GetMapping("/videos/{articleCategoryId}")
    public String videoCategory(@PathVariable Long articleCategoryId, @CurrentUser Business currentUser,  Integer pageNumber, ModelMap model){
        model.addAttribute("currentStoreUser",currentUser);
        Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
        Page<Article> articlePage=articleService.findPage(articleCategoryService.find(articleCategoryId), null, true, pageable);
        articlePage.getContent().stream().forEach(temp->{
            int start=temp.getContent().indexOf("http");
            int end=temp.getContent().indexOf("mp4");
            String tempsubStr=temp.getContent().substring(start,end+3);
            temp.setContent(tempsubStr);
        });
        model.addAttribute("fiber",articlePage);
        return "/shop/datacenter/datacenter";
    }

    /***
     * 视频详情
     * @param model
     * @param articleId
     * @return
     */
    @GetMapping("/detail/{articleId}_{pageNumber}")
    public String videoDetail(ModelMap model,@PathVariable Long articleId, @PathVariable Integer pageNumber,@CurrentUser Business currentUser){
        model.addAttribute("currentStoreUser",currentUser);
        Article article = articleService.find(articleId);
        if(ConvertUtils.isNotEmpty(article)){
            int start=article.getContent().indexOf("http");
            int end=article.getContent().indexOf("mp4");
            String tempsubStr=article.getContent().substring(start,end+3);
            article.setContent(tempsubStr);
        }
        model.addAttribute("video",article);
        model.addAttribute("pageNumber", pageNumber);
        return "/shop/datacenter/detail";
    }
}
