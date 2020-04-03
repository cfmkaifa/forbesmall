/*
 *
 *
 *
 *
 */
package net.mall.controller.shop;

import java.util.*;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.annotation.JsonView;

import net.mall.Filter;
import net.mall.Page;
import net.mall.Pageable;
import net.mall.Results;
import net.mall.entity.Article;
import net.mall.entity.ArticleCategory;
import net.mall.entity.BaseEntity;
import net.mall.entity.Business;
import net.mall.entity.Member;
import net.mall.entity.MemberRank;
import net.mall.entity.Sn;
import net.mall.entity.StoreRank;
import net.mall.entity.SubsNewsHuman;
import net.mall.exception.ResourceNotFoundException;
import net.mall.plugin.PaymentPlugin;
import net.mall.security.CurrentUser;
import net.mall.service.ArticleCategoryService;
import net.mall.service.ArticleService;
import net.mall.service.MemberRankService;
import net.mall.service.PluginService;
import net.mall.service.SnService;
import net.mall.service.StoreRankService;
import net.mall.service.SubsNewsHumanService;
import net.mall.util.ConvertUtils;
import net.mall.util.WebUtils;

/**
 * Controller - 文章
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("shopArticleController")
@RequestMapping("/article")
public class ArticleController extends BaseController {

    /**
     * 每页记录数
     */
    private static final int PAGE_SIZE = 20;

    @Inject
    private ArticleService articleService;
    @Inject
    private SubsNewsHumanService subsNewsHumanService;
    @Inject
    private ArticleCategoryService articleCategoryService;
    @Inject
    MemberRankService memberRankService;
    @Inject
    private PluginService pluginService;
    @Inject
    SnService snService;
    @Inject
    private StoreRankService storeRankService;

    /**
     * 详情
     */
    @GetMapping("/detail/{articleId}_{pageNumber}")
    public String detail(@PathVariable Long articleId, @PathVariable Integer pageNumber,
                         @CurrentUser Business currentBusiness,
                         @CurrentUser Member currentMember, ModelMap model) {
        Article article = articleService.find(articleId);
        if (article == null || pageNumber < 1 || pageNumber > article.getTotalPages()) {
            throw new ResourceNotFoundException();
        }
        if (article.getArticleCategory() == null) {
            throw new ResourceNotFoundException();
        }
        if (ArticleCategory.Subscribe.YES.equals(article.getArticleCategory().getSubscribe())) {
            Long humanId = null;
            if (ConvertUtils.isNotEmpty(currentBusiness)) {
                humanId = currentBusiness.getId();
            }
            if (ConvertUtils.isNotEmpty(currentMember)) {
                humanId = currentMember.getId();
            }
            Filter humanIdFilter = new Filter("humanId", Filter.Operator.EQ, humanId);
            Filter articleCategoryIdFilter = new Filter("dataId", Filter.Operator.EQ, article.getArticleCategory().getId());
            Filter expdFilter = new Filter("expd", Filter.Operator.LE, new Date());
            long subsCount = subsNewsHumanService.count(humanIdFilter, articleCategoryIdFilter, expdFilter);
            if (subsCount > 0) {
                model.addAttribute("isPerm", true);
            } else {
                model.addAttribute("isPerm", false);
                List<MemberRank> memberRanks = memberRankService.findAll();
                model.addAttribute("memberRanks", memberRanks);
            }
        } else {
            model.addAttribute("isPerm", true);
        }
        model.addAttribute("article", article);
        model.addAttribute("pageNumber", pageNumber);
        Pageable pageable = new Pageable(pageNumber, 5);
        ArticleCategory articleCategory = articleCategoryService.find(article.getArticleCategory().getId());
        model.addAttribute("page", articleService.findPage(articleCategory, null, true, pageable));
        return "shop/article/details";
    }

    /****
     * newsList方法慨述:新闻列表
     * @param model
     * @return String
     * @创建人 huanghy
     * @创建时间 2020年1月8日 上午11:12:26
     * @修改人 (修改了该文件 ， 请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    @GetMapping("/news")
    public String newsList(ModelMap model) {
        List<ArticleCategory> articleCategorys = articleCategoryService.findRoots(ArticleCategory.Type.NEWS);
        model.addAttribute("articleCategorys", articleCategorys);
        return "shop/article/news/list";
    }


    /**
     * 列表
     */
    @GetMapping("/list/{articleCategoryId}")
    public String list(@PathVariable Long articleCategoryId, Integer pageNumber,
                       @CurrentUser Business currentBusiness,
                       @CurrentUser Member currentMember,
                       ModelMap model) {
        ArticleCategory articleCategory = articleCategoryService.find(articleCategoryId);
        if (articleCategory == null) {
            throw new ResourceNotFoundException();
        }
        if (ArticleCategory.Subscribe.YES.equals(articleCategory.getSubscribe())) {
            Long humanId = null;
            if (ConvertUtils.isNotEmpty(currentBusiness)) {
                humanId = currentBusiness.getId();
            }
            if (ConvertUtils.isNotEmpty(currentMember)) {
                humanId = currentMember.getId();
            }
            Filter humanIdFilter = new Filter("humanId", Filter.Operator.EQ, humanId);
            Filter articleCategoryIdFilter = new Filter("dataId", Filter.Operator.EQ, articleCategoryId);
            Filter expdFilter = new Filter("expd", Filter.Operator.LE, new Date());
            long subsCount = subsNewsHumanService.count(humanIdFilter, articleCategoryIdFilter, expdFilter);
            if (subsCount > 0) {
                model.addAttribute("isPerm", true);
            } else {
                model.addAttribute("isPerm", false);
                List<MemberRank> memberRanks = memberRankService.findAll();
                model.addAttribute("memberRanks", memberRanks);
            }
        } else {
            model.addAttribute("isPerm", true);
        }
        Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
        model.addAttribute("articleCategory", articleCategory);
        model.addAttribute("page", articleService.findPage(articleCategory, null, true, pageable));
        return "shop/article/list";
    }

    /**
     * @return java.lang.String
     * 文章列表
     * @Author xfx
     * @Date 15:02 2020/1/9
     * @Param [articleCategoryId, pageNumber, currentBusiness, currentMember, model]
     **/
    @GetMapping("/articlelist/{articleCategoryId}")
    public String articlelist(@PathVariable Long articleCategoryId, Integer pageNumber,
                              @CurrentUser Business currentBusiness,
                              @CurrentUser Member currentMember,
                              ModelMap model) {
        ArticleCategory articleCategory = articleCategoryService.find(articleCategoryId);
        if (articleCategory == null) {
            throw new ResourceNotFoundException();
        }
        if (ArticleCategory.Subscribe.YES.equals(articleCategory.getSubscribe())) {
            Long humanId = null;
            if (ConvertUtils.isNotEmpty(currentBusiness)) {
                humanId = currentBusiness.getId();
            }
            if (ConvertUtils.isNotEmpty(currentMember)) {
                humanId = currentMember.getId();
            }
            Filter humanIdFilter = new Filter("humanId", Filter.Operator.EQ, humanId);
            Filter articleCategoryIdFilter = new Filter("dataId", Filter.Operator.EQ, articleCategoryId);
            Filter expdFilter = new Filter("expd", Filter.Operator.LE, new Date());
            long subsCount = subsNewsHumanService.count(humanIdFilter, articleCategoryIdFilter, expdFilter);
            if (subsCount > 0) {
                model.addAttribute("isPerm", true);
            } else {
                model.addAttribute("isPerm", false);
                List<MemberRank> memberRanks = memberRankService.findAll();
                model.addAttribute("memberRanks", memberRanks);
            }
        } else {
            model.addAttribute("isPerm", true);
        }
        List<ArticleCategory> articleCategories = articleCategoryService.findRoots(6, ArticleCategory.Type.NEWS);
        Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
        model.addAttribute("articleCategory", articleCategory);
        model.addAttribute("page", articleService.findPage(articleCategory, null, true, pageable));
        model.addAttribute("articleCategories", articleCategories);
        model.addAttribute("articleCategoryId",articleCategoryId);
        return "shop/article/attention";
    }

    /**
     * 列表
     */
    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(BaseEntity.BaseView.class)
    public ResponseEntity<?> list(Long articleCategoryId, Integer pageNumber) {
        ArticleCategory articleCategory = articleCategoryService.find(articleCategoryId);
        if (articleCategory == null) {
            return Results.NOT_FOUND;
        }
        Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
        return ResponseEntity.ok(articleService.findPage(articleCategory, null, true, pageable).getContent());
    }

    /**
     * 搜索
     */
    @GetMapping("/search")
    public String search(String keyword, Integer pageNumber, ModelMap model) {
        if (StringUtils.isEmpty(keyword)) {
            return UNPROCESSABLE_ENTITY_VIEW;
        }
        Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
        model.addAttribute("articleKeyword", keyword);
        model.addAttribute("page", articleService.search(keyword, pageable));
        return "shop/article/search";
    }

    /**
     * 搜索
     */
    @GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(BaseEntity.BaseView.class)
    public ResponseEntity<?> search(String keyword, Integer pageNumber) {
        if (StringUtils.isEmpty(keyword)) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
        return ResponseEntity.ok(articleService.search(keyword, pageable).getContent());
    }

    /**
     * 点击数
     */
    @GetMapping("/hits/{articleId}")
    public ResponseEntity<?> hits(@PathVariable Long articleId) {
        if (articleId == null) {
            return Results.UNPROCESSABLE_ENTITY;
        }

        Map<String, Object> data = new HashMap<>();
        data.put("hits", articleService.viewHits(articleId));
        return ResponseEntity.ok(data);
    }


    /**
     * @Author xfx
     * @Date 9:49 2020/1/3
     * 会员介绍
     **/
    @GetMapping("/member")
    public String member(Pageable pageable, ModelMap model) {
        Page<StoreRank> page = storeRankService.findPage(pageable);
        model.addAttribute("page", page.getContent());
        return "shop/article/members";
    }

    /**
     * @Author xfx
     * @Date 9:36 2020/1/9
     * 新闻资讯跳转
     **/
    @GetMapping("/articleindex")
    public String article(ModelMap model) {
        List<ArticleCategory> articleCategories = articleCategoryService.findRoots(6, ArticleCategory.Type.NEWS);
        Map<String, Page<Article>> map = new HashMap<>();
        Pageable pageable = new Pageable();
        pageable.setPageSize(8);
        Pageable temppage = new Pageable();
        temppage.setPageSize(5);
        model.addAttribute("instantnews", articleService.findPage(articleCategoryService.find(articleCategories.get(1).getId()), null, true, temppage));
        model.addAttribute("fubuinsights", articleService.findPage(articleCategoryService.find(articleCategories.get(2).getId()), null, true, pageable));
        model.addAttribute("factorystatus", articleService.findPage(articleCategoryService.find(articleCategories.get(3).getId()), null, true, pageable));
        model.addAttribute("commonality", articleService.findPage(articleCategoryService.find(articleCategories.get(5).getId()), null, true, pageable));
        model.addAttribute("articleCategories", articleCategories);
        return "shop/article/news";
    }


    /***
     * subscribe方法慨述:订阅方法
     * @param articleCategoryId
     * @param model
     * @return String
     * @创建人 huanghy
     * @创建时间 2020年1月4日 下午12:02:05
     * @修改人 (修改了该文件 ， 请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    @GetMapping(value = {"/subscribe-supplier/{articleCategoryId}/{subType}", "/subscribe-purchaser/{articleCategoryId}/{subType}"})
    public String subscribe(@PathVariable Long articleCategoryId,
                            @PathVariable String subType,
                            @CurrentUser Business currentBusiness,
                            @CurrentUser Member currentMember,
                            ModelMap model) {
        ArticleCategory articleCategory = articleCategoryService.find(articleCategoryId);
        if (articleCategory == null) {
            throw new ResourceNotFoundException();
        }
        List<PaymentPlugin> paymentPlugins = pluginService.getActivePaymentPlugins(WebUtils.getRequest());
        if (!paymentPlugins.isEmpty()) {
            model.addAttribute("defaultPaymentPlugin", paymentPlugins.get(0));
            model.addAttribute("paymentPlugins", paymentPlugins);
        }
        model.addAttribute("articleCategory", articleCategory);
        if (subType.equals("weekSubFee")) {
            model.addAttribute("subFee", articleCategory.getWeekSubFee());
        }
        if (subType.equals("monthSubFee")) {
            model.addAttribute("subFee", articleCategory.getMonthSubFee());
        }
        if (subType.equals("quarterSubFee")) {
            model.addAttribute("subFee", articleCategory.getQuarterSubFee());
        }
        if (subType.equals("yearSubFee")) {
            model.addAttribute("subFee", articleCategory.getYearSubFee());
        }
        model.addAttribute("articleCategory", articleCategory);
        SubsNewsHuman subsNewsHuman = new SubsNewsHuman();
        if (ConvertUtils.isNotEmpty(currentMember)) {
            subsNewsHuman.setHumanId(currentMember.getId());
        }
        if (ConvertUtils.isNotEmpty(currentBusiness)) {
            subsNewsHuman.setHumanId(currentBusiness.getId());
        }
        // 订单项
        String orderSn = snService.generate(Sn.Type.NEWS_SUBSCRIBE_PAYMENT);
        subsNewsHuman.setDataType(subType);
        subsNewsHuman.setDataId(articleCategoryId);
        subsNewsHuman.setSn(orderSn);
        subsNewsHumanService.save(subsNewsHuman);
        model.addAttribute("orderSn", orderSn);
        return "shop/article/subscribe";
    }
}