package com.vodworks.service.impl;

import com.vodworks.domain.Campaign;
import com.vodworks.domain.CampaignCompBrand;
import com.vodworks.domain.Question;
import com.vodworks.domain.enumeration.CampaignBrandCompType;
import com.vodworks.repository.CampaignCompBrandRepository;
import com.vodworks.repository.CampaignRepository;
import com.vodworks.repository.QuestionRepository;
import com.vodworks.service.QuestionService;
import com.vodworks.service.QuestionsTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing {@link Question}.
 */
@Service
@Transactional
//@ConfigurationProperties(prefix = "application.question",ignoreUnknownFields = false)
public class QuestionServiceImpl implements QuestionService {

    private final Logger log = LoggerFactory.getLogger(QuestionServiceImpl.class);

    @Value("${question.rate-identify.parameters}")
    private String rateIdentifyParameters;// = "Cautious:Open,Spontaneous:Conscientious,Introverted:Extroverted,Challenging:Cooperative,Calm:Passionate";

    @Value("${question.rate-creative.parameters}")
    private String rateCreativeParameters;// = "Find out more about the product in the post?,Purchase the product in the post?,Share this post on social media?";

    private final QuestionRepository questionRepository;

    private final CampaignRepository campaignRepository;

    private final CampaignCompBrandRepository campaignCompBrandRepository;

    private final QuestionsTemplateService questionsTemplateService;

    public QuestionServiceImpl(QuestionRepository questionRepository, CampaignRepository campaignRepository, CampaignCompBrandRepository campaignCompBrandRepository, QuestionsTemplateService questionsTemplateService) {
        this.questionRepository = questionRepository;
        this.campaignRepository = campaignRepository;
        this.campaignCompBrandRepository = campaignCompBrandRepository;
        this.questionsTemplateService = questionsTemplateService;
    }

    @Override
    public void createCampaignQuestions(Campaign campaign, CampaignCompBrand campaignBrand, List<CampaignCompBrand> campaignCompetitors ) {

        //Saving Questions after Populating the Templates

        String questionStr = "";

        //CampaignCompBrand campaignBrand = this.campaignCompBrandRepository.findAllByCampaignAndAndType(campaign, CampaignBrandCompType.BRAND).get(0);
        //List<CampaignCompBrand> campaignCompetitors = this.campaignCompBrandRepository.findAllByCampaignAndAndType(campaign, CampaignBrandCompType.COMP);

        String brandLogoImageUrl = campaignBrand.getImageFileUrl();
        String competitor1LogoImageUrl = campaignCompetitors.get(0).getImageFileUrl();
        String competitor2LogoImageUrl = campaignCompetitors.get(1).getImageFileUrl();

        //6.1: generateRateIdentifyQuestions

        String[] rateIdentifyQuestions = rateIdentifyParameters.split(",");

        for (String rateIdentifyQuestion : rateIdentifyQuestions) {

            String[] rateIdentifyQuestionsSides = rateIdentifyQuestion.split(":");
            questionStr = questionsTemplateService.generateRateIdentifyQuestions(rateIdentifyQuestionsSides[0], rateIdentifyQuestionsSides[1]);

            this.saveQuestion(questionStr, campaign);
        }

        //6.2: generateTextThinkOfQuestion
        questionStr = questionsTemplateService.generateTextThinkOfQuestion(campaign.getCategory());

        this.saveQuestion(questionStr, campaign);

        //6.3: generateBrandsHeardOfQuestion

        questionStr = questionsTemplateService.generateBrandsHeardOfQuestion(campaignBrand, campaignCompetitors);

        this.saveQuestion(questionStr, campaign);

        //6.4: generateRateOpinionQuestion

        questionStr = questionsTemplateService.generateRateOpinionQuestion(brandLogoImageUrl);

        this.saveQuestion(questionStr, campaign);

        questionStr = questionsTemplateService.generateRateOpinionQuestion(competitor1LogoImageUrl);

        this.saveQuestion(questionStr, campaign);

        questionStr = questionsTemplateService.generateRateOpinionQuestion(competitor2LogoImageUrl);

        this.saveQuestion(questionStr, campaign);

        //6.5: generateRatePurchaseQuestion

        questionStr = questionsTemplateService.generateRatePurchaseQuestion(campaign.getCategory(), brandLogoImageUrl);

        this.saveQuestion(questionStr, campaign);

        questionStr = questionsTemplateService.generateRatePurchaseQuestion(campaign.getCategory(), competitor1LogoImageUrl);

        this.saveQuestion(questionStr, campaign);

        questionStr = questionsTemplateService.generateRatePurchaseQuestion(campaign.getCategory(), competitor2LogoImageUrl);

        this.saveQuestion(questionStr, campaign);

        //6.6: generateYesNoQuestion

        questionStr = questionsTemplateService.generateYesNoQuestion(campaign.getBrandFirstTrait(), brandLogoImageUrl);

        this.saveQuestion(questionStr, campaign);

        questionStr = questionsTemplateService.generateYesNoQuestion(campaign.getBrandFirstTrait(), competitor1LogoImageUrl);

        this.saveQuestion(questionStr, campaign);

        questionStr = questionsTemplateService.generateYesNoQuestion(campaign.getBrandFirstTrait(), competitor2LogoImageUrl);

        this.saveQuestion(questionStr, campaign);

        questionStr = questionsTemplateService.generateYesNoQuestion(campaign.getBrandSecondTrait(), brandLogoImageUrl);

        this.saveQuestion(questionStr, campaign);

        questionStr = questionsTemplateService.generateYesNoQuestion(campaign.getBrandSecondTrait(), competitor1LogoImageUrl);

        this.saveQuestion(questionStr, campaign);

        questionStr = questionsTemplateService.generateYesNoQuestion(campaign.getBrandSecondTrait(), competitor2LogoImageUrl);

        this.saveQuestion(questionStr, campaign);

        questionStr = questionsTemplateService.generateYesNoQuestion(campaign.getBrandThirdTrait(), brandLogoImageUrl);

        this.saveQuestion(questionStr, campaign);

        questionStr = questionsTemplateService.generateYesNoQuestion(campaign.getBrandThirdTrait(), competitor1LogoImageUrl);

        this.saveQuestion(questionStr, campaign);

        questionStr = questionsTemplateService.generateYesNoQuestion(campaign.getBrandThirdTrait(), competitor2LogoImageUrl);

        this.saveQuestion(questionStr, campaign);

        //6.7: generateRateCreativeQuestion

        String[] rateCreativeQuestions = rateCreativeParameters.split(",");

        for (String rateCreativeQuestion : rateCreativeQuestions) {

            questionStr = questionsTemplateService.generateRateCreativeQuestion(rateCreativeQuestion);
            this.saveQuestion(questionStr, campaign);
        }

        //6.8 generateSingleChoiceHorizontalQuestion

        //questionStr = campaignQuestion.generateSingleChoiceHorizontalQuestion("", "", "", "", "", "");

        //this.saveQuestion(questionStr, campaign);

        //6.9 generateTextRememberQuestion

        questionStr = questionsTemplateService.generateTextRememberQuestion();

        this.saveQuestion(questionStr, campaign);

        //6.10 generateRatePurchaseAgainQuestion

        questionStr = questionsTemplateService.generateRatePurchaseAgainQuestion(brandLogoImageUrl, competitor1LogoImageUrl, competitor2LogoImageUrl);

        this.saveQuestion(questionStr, campaign);

        //6.11 generateRateRecommendQuestion

        questionStr = questionsTemplateService.generateRateRecommendQuestion(brandLogoImageUrl, competitor1LogoImageUrl, competitor2LogoImageUrl);

        this.saveQuestion(questionStr, campaign);

        //6.12 generateRateInfluencerQuestion

        questionStr = questionsTemplateService.generateRateInfluencerQuestion();

        this.saveQuestion(questionStr, campaign);

        //6.13 generateSubInfluencerQuestion

        questionStr = questionsTemplateService.generateSubInfluencerQuestion();

        this.saveQuestion(questionStr, campaign);

        //6.14 generateSubLikelyQuestion

        questionStr = questionsTemplateService.generateSubLikelyQuestion();

        this.saveQuestion(questionStr, campaign);

        //6.15 generateSubGridCreativeEmotionQuestion

        questionStr = questionsTemplateService.generateSubGridCreativeEmotionQuestion();

        this.saveQuestion(questionStr, campaign);
    }

    private void saveQuestion(String questionStr, Campaign campaign) {

        Question question = new Question();

        question.setCampaign(campaign);
        question.setJsonBody(questionStr);

        this.questionRepository.save(question);
    }
}
