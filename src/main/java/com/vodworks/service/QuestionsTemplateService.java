package com.vodworks.service;

import com.vodworks.domain.CampaignCompBrand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QuestionsTemplateService {

    private final Logger log = LoggerFactory.getLogger(QuestionsTemplateService.class);

    private String rateIdentify = "{\"type\":\"rate-identify\",\"title\":\"\",\"statement\":\"I identify as:\",\"leftEnd\":{\"type\":\"text\",\"label\":\"<LEFT_LABEL>\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"<RIGHT_LABEL>\",\"valueNumber\":100}}";

    private String textThinkOf = "{\"type\":\"text-thinkof\",\"title\":\"Brand Questions\",\"statement\":\"When you think of <BRAND_CATEGORY>, what brands have you heard of?\",\"textLimit\":1000}";

    private String brandsHeardOf = "{\"type\":\"brands-heard-of\",\"title\":\"Brand Questions\",\"statement\":\"Which of these brands have you heard of?\",\"questions\":[{\"id\":0,\"controlType\":\"image-selection\",\"valueString\":\"<BRAND_NAME>\",\"label\":\"<BRAND_LOGO_URL>\"},{\"id\":1,\"controlType\":\"image-selection\",\"valueString\":\"<COMPETITOR_1_NAME>\",\"label\":\"<COMPETITOR_1_LOGO_URL>\"},{\"id\":2,\"controlType\":\"image-selection\",\"valueString\":\"<COMPETITOR_2_NAME>\",\"label\":\"<COMPETITOR_2_LOGO_URL>\"}]}";

    private String rateOpinion = "{\"type\":\"rate-opinion\",\"title\":\"Brand Questions\",\"statement\":\"What is your opinion of these brands? (1 - negative, 10 - positive)\",\"statementImageUrl\":\"<COMPETITOR_LOGO_URL>\"}";

    private String ratePurchase = "{\"type\":\"rate-purchase\",\"title\":\"Brand Questions\",\"statement\":\"How likely would you be to purchase the following <BRAND_CATEGORY> products, in the next 3  months?\",\"statementImageUrl\":\"<COMPETITOR_LOGO_URL>\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Very unlikely\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Very likely\",\"valueNumber\":100}}";

    private String yesNo = "{\"type\":\"yes-no\",\"title\":\"\",\"statement\":\"<BRAND_TRAIT>\",\"option\":{\"type\":\"imgUrl\",\"valueString\":\"yes/no\",\"label\":\"<COMPETITOR_LOGO_URL>\"}}";

    private String rateCreative = "{\"type\":\"rate-creative\",\"title\":\"Creative Questions\",\"statement\":\"<CREATIVE_QUESTION_STATEMENT>\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Very unlikely\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Very likely\",\"valueNumber\":100}}";

    private String textRemember = "{\"type\":\"text-remember\",\"title\":\"Creative Questions\",\"statement\":\"Which brands did you see in the post?\",\"textLimit\":1000,\"answerOptions\":[{\"type\":\"text\",\"valueString\":\"I can't remember\",\"label\":\"I can't remember\"},{\"type\":\"text\",\"valueString\":\"I couldn't tell\",\"label\":\"I couldn't tell\"},{\"type\":\"text\",\"valueString\":\"There was no brand\",\"label\":\"There was no brand\"}]}";

    private String ratePurchaseAgain = "{\"type\":\"rate-purchase-again\",\"title\":\"Brand Questions\",\"statement\":\"How likely would you be to continue purchasing the following brands time and again?\",\"questions\":[{\"id\":0,\"controlType\":\"image-slider\",\"statementImageUrl\":\"<BRAND_LOGO_URL>\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Very unlikely\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Very likely\",\"valueNumber\":100}},{\"id\":1,\"controlType\":\"image-slider\",\"statementImageUrl\":\"<COMPETITOR_1_LOGO_URL>\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Very unlikely\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Very likely\",\"valueNumber\":100}},{\"id\":2,\"controlType\":\"image-slider\",\"statementImageUrl\":\"<COMPETITOR_2_LOGO_URL>\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Very unlikely\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Very likely\",\"valueNumber\":100}}]}";

    private String rateRecommend = "{\"type\":\"rate-recommend\",\"title\":\"Brand Questions\",\"statement\":\"How likely would you be to recommend the following brands to friends and family?\",\"questions\":[{\"id\":0,\"controlType\":\"image-slider\",\"statementImageUrl\":\"<BRAND_LOGO_URL>\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Very unlikely\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Very likely\",\"valueNumber\":100}},{\"id\":1,\"controlType\":\"image-slider\",\"statementImageUrl\":\"<COMPETITOR_1_LOGO_URL>\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Very unlikely\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Very likely\",\"valueNumber\":100}},{\"id\":2,\"controlType\":\"image-slider\",\"statementImageUrl\":\"<COMPETITOR_2_LOGO_URL>\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Very unlikely\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Very likely\",\"valueNumber\":100}}]}";

    private String rateInfluencer = "{\"type\":\"rate-influencer\",\"title\":\"Influencer Questions\",\"statement\":\"To what extent did the influencer fit the brand in the post?\",\"controlType\":\"text-slider\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not at all\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"A great deal\",\"valueNumber\":100}}";

    private String subInfluencer = "{\"type\":\"sub-influencer\",\"title\":\"Influencer Questions\",\"statement\":\"To what extent do you think the influencer is:\",\"questions\":[{\"id\":0,\"controlType\":\"text-slider\",\"statement\":\"Genuine?\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not at all\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"A great deal\",\"valueNumber\":100}},{\"id\":1,\"controlType\":\"text-slider\",\"statement\":\"Relevant to you?\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not at all\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"A great deal\",\"valueNumber\":100}},{\"id\":2,\"controlType\":\"text-slider\",\"statement\":\"Credible?\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not at all\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"A great deal\",\"valueNumber\":100}},{\"id\":3,\"controlType\":\"text-slider\",\"statement\":\"Cool?\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not at all\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"A great deal\",\"valueNumber\":100}},{\"id\":4,\"controlType\":\"text-slider\",\"statement\":\"Relatable?\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not at all\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"A great deal\",\"valueNumber\":100}},{\"id\":5,\"controlType\":\"text-slider\",\"statement\":\"Original?\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not at all\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"A great deal\",\"valueNumber\":100}},{\"id\":6,\"controlType\":\"text-slider\",\"statement\":\"Innovative?\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not at all\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"A great deal\",\"valueNumber\":100}},{\"id\":7,\"controlType\":\"text-slider\",\"statement\":\"Empowering?\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not at all\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"A great deal\",\"valueNumber\":100}},{\"id\":8,\"controlType\":\"text-slider\",\"statement\":\"Trustworthy?\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not at all\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"A great deal\",\"valueNumber\":100}}]}";

    private String subLikely = "{\"type\":\"sub-likely\",\"title\":\"Influencer Questions\",\"statement\":\"How likely are you to:\",\"questions\":[{\"id\":0,\"controlType\":\"text-slider\",\"statement\":\"Recommend the influencer to your friends or family?\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Very unlikely\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Very likely\",\"valueNumber\":100}},{\"id\":1,\"controlType\":\"text-slider\",\"statement\":\"Be inspired by the influencer?\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Very unlikely\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Very likely\",\"valueNumber\":100}},{\"id\":2,\"controlType\":\"text-slider\",\"statement\":\"Purchase the products promoted by the influencer?\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Very unlikely\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Very likely\",\"valueNumber\":100}}]}";

    private String subGridCreativeEmotion = "{\"type\":\"sub-grid-creative-emotion\",\"title\":\"Creative Questions\",\"statement\":\"What emotions did you feel while viewing this post?\",\"subStatement\":\"For the emotions you have selected, how intensely did you feel them?\",\"questions\":[{\"id\":0,\"controlType\":\"text-slider\",\"statement\":\"Sympathy\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not very strongly\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Extremely strongly\",\"valueNumber\":100}},{\"id\":1,\"controlType\":\"text-slider\",\"statement\":\"Satisfaction\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not very strongly\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Extremely strongly\",\"valueNumber\":100}},{\"id\":2,\"controlType\":\"text-slider\",\"statement\":\"Admiration\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not very strongly\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Extremely strongly\",\"valueNumber\":100}},{\"id\":3,\"controlType\":\"text-slider\",\"statement\":\"Excitement\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not very strongly\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Extremely strongly\",\"valueNumber\":100}},{\"id\":4,\"controlType\":\"text-slider\",\"statement\":\"Triumph\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not very strongly\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Extremely strongly\",\"valueNumber\":100}},{\"id\":5,\"controlType\":\"text-slider\",\"statement\":\"Warmth\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not very strongly\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Extremely strongly\",\"valueNumber\":100}},{\"id\":6,\"controlType\":\"text-slider\",\"statement\":\"Nostalgia\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not very strongly\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Extremely strongly\",\"valueNumber\":100}},{\"id\":7,\"controlType\":\"text-slider\",\"statement\":\"Awe\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not very strongly\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Extremely strongly\",\"valueNumber\":100}},{\"id\":8,\"controlType\":\"text-slider\",\"statement\":\"Amusement\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not very strongly\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Extremely strongly\",\"valueNumber\":100}},{\"id\":9,\"controlType\":\"text-slider\",\"statement\":\"Romance\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not very strongly\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Extremely strongly\",\"valueNumber\":100}},{\"id\":10,\"controlType\":\"text-slider\",\"statement\":\"Interest\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not very strongly\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Extremely strongly\",\"valueNumber\":100}},{\"id\":11,\"controlType\":\"text-slider\",\"statement\":\"Calmness\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not very strongly\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Extremely strongly\",\"valueNumber\":100}},{\"id\":12,\"controlType\":\"text-slider\",\"statement\":\"Sexual desire\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not very strongly\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Extremely strongly\",\"valueNumber\":100}},{\"id\":13,\"controlType\":\"text-slider\",\"statement\":\"Aesthetic appreciation\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not very strongly\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Extremely strongly\",\"valueNumber\":100}},{\"id\":14,\"controlType\":\"text-slider\",\"statement\":\"Inspiration\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not very strongly\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Extremely strongly\",\"valueNumber\":100}},{\"id\":15,\"controlType\":\"text-slider\",\"statement\":\"Joy\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not very strongly\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Extremely strongly\",\"valueNumber\":100}},{\"id\":16,\"controlType\":\"text-slider\",\"statement\":\"Adoration\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not very strongly\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Extremely strongly\",\"valueNumber\":100}},{\"id\":17,\"controlType\":\"text-slider\",\"statement\":\"Entrancement\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not very strongly\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Extremely strongly\",\"valueNumber\":100}},{\"id\":18,\"controlType\":\"text-slider\",\"statement\":\"Horror\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not very strongly\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Extremely strongly\",\"valueNumber\":100}},{\"id\":19,\"controlType\":\"text-slider\",\"statement\":\"Disgust\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not very strongly\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Extremely strongly\",\"valueNumber\":100}},{\"id\":20,\"controlType\":\"text-slider\",\"statement\":\"Fear\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not very strongly\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Extremely strongly\",\"valueNumber\":100}},{\"id\":21,\"controlType\":\"text-slider\",\"statement\":\"Confusion\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not very strongly\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Extremely strongly\",\"valueNumber\":100}},{\"id\":22,\"controlType\":\"text-slider\",\"statement\":\"Sadness\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not very strongly\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Extremely strongly\",\"valueNumber\":100}},{\"id\":23,\"controlType\":\"text-slider\",\"statement\":\"Craving\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not very strongly\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Extremely strongly\",\"valueNumber\":100}},{\"id\":24,\"controlType\":\"text-slider\",\"statement\":\"Anxiety\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not very strongly\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Extremely strongly\",\"valueNumber\":100}},{\"id\":25,\"controlType\":\"text-slider\",\"statement\":\"Envy\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not very strongly\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Extremely strongly\",\"valueNumber\":100}},{\"id\":26,\"controlType\":\"text-slider\",\"statement\":\"Empathetic pain\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not very strongly\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Extremely strongly\",\"valueNumber\":100}},{\"id\":27,\"controlType\":\"text-slider\",\"statement\":\"Awkwardness\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not very strongly\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Extremely strongly\",\"valueNumber\":100}},{\"id\":28,\"controlType\":\"text-slider\",\"statement\":\"Boredom\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not very strongly\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Extremely strongly\",\"valueNumber\":100}},{\"id\":29,\"controlType\":\"text-slider\",\"statement\":\"Contempt\",\"leftEnd\":{\"type\":\"text\",\"label\":\"Not very strongly\",\"valueNumber\":0},\"rightEnd\":{\"type\":\"text\",\"label\":\"Extremely strongly\",\"valueNumber\":100}}]}";

    //Will be caled 5 times
    public String generateRateIdentifyQuestions(String leftLabel, String rightLabel) {

        String raterateIdentifyQuestion = "";

        raterateIdentifyQuestion = rateIdentify.replace("<LEFT_LABEL>", leftLabel);
        raterateIdentifyQuestion = raterateIdentifyQuestion.replace("<RIGHT_LABEL>", rightLabel);

        return raterateIdentifyQuestion;
    }

    //Will be caled 1 times
    public String generateTextThinkOfQuestion(String category) {

        String textThinkOfQuestion = "";

        textThinkOfQuestion = textThinkOf.replace("<BRAND_CATEGORY>", category);

        return textThinkOfQuestion;
    }

    //Will be called 1 times
    public String generateBrandsHeardOfQuestion(CampaignCompBrand brand, List<CampaignCompBrand> competitors) {

        String singleChoiceVerticalQuestion = "";

        singleChoiceVerticalQuestion = brandsHeardOf.replace("<BRAND_NAME>", brand.getCompBrandName());
        singleChoiceVerticalQuestion = singleChoiceVerticalQuestion.replace("<BRAND_LOGO_URL>", brand.getImageFileUrl());
        singleChoiceVerticalQuestion = singleChoiceVerticalQuestion.replace("<COMPETITOR_1_NAME>", competitors.get(0).getCompBrandName());
        singleChoiceVerticalQuestion = singleChoiceVerticalQuestion.replace("<COMPETITOR_1_LOGO_URL>", competitors.get(0).getImageFileUrl());
        singleChoiceVerticalQuestion = singleChoiceVerticalQuestion.replace("<COMPETITOR_2_NAME>", competitors.get(1).getCompBrandName());
        singleChoiceVerticalQuestion = singleChoiceVerticalQuestion.replace("<COMPETITOR_2_LOGO_URL>", competitors.get(1).getImageFileUrl());

        return singleChoiceVerticalQuestion;
    }

    //Will be called 3 times
    public String generateRateOpinionQuestion(String compLogoUrl) {

        String rateOpinionQuestion = "";

        rateOpinionQuestion = rateOpinion.replace("<COMPETITOR_LOGO_URL>", compLogoUrl);

        return rateOpinionQuestion;
    }

    //Will be called 3 times
    public String generateRatePurchaseQuestion(String brandCategory, String compLogoUrl) {

        String ratePurchaseQuestion = "";

        ratePurchaseQuestion = ratePurchase.replace("<BRAND_CATEGORY>", brandCategory);
        ratePurchaseQuestion = ratePurchaseQuestion.replace("<COMPETITOR_LOGO_URL>", compLogoUrl);

        return ratePurchaseQuestion;
    }

    //Will be called 3*3 = 9 times
    public String generateYesNoQuestion(String brandTrait, String compLogoUrl) {

        String yesNoQuestion = "";

        yesNoQuestion = yesNo.replace("<BRAND_TRAIT>", brandTrait);
        yesNoQuestion = yesNoQuestion.replace("<COMPETITOR_LOGO_URL>", compLogoUrl);

        return yesNoQuestion;
    }

    //Will be called 3 times
    public String generateRateCreativeQuestion(String questionStatement) {

        String rateCreativeQuestion = "";

        rateCreativeQuestion = rateCreative.replace("<CREATIVE_QUESTION_STATEMENT>", questionStatement);

        return rateCreativeQuestion;
    }

    //Will be called 1 time
    public String generateTextRememberQuestion() {

        String textRememberQuestion = "";

        textRememberQuestion = textRemember;

        return textRememberQuestion;
    }

    //Will be called 1 time
    public String generateRatePurchaseAgainQuestion(String brandLogoUrl, String competitor1LogoUrl, String competitor2LogoUrl) {

        String ratePurchaseAgainQuestion = "";

        ratePurchaseAgainQuestion = ratePurchaseAgain.replace("<BRAND_LOGO_URL>", brandLogoUrl);
        ratePurchaseAgainQuestion = ratePurchaseAgainQuestion.replace("<COMPETITOR_1_LOGO_URL>", competitor1LogoUrl);
        ratePurchaseAgainQuestion = ratePurchaseAgainQuestion.replace("<COMPETITOR_2_LOGO_URL>", competitor2LogoUrl);

        return ratePurchaseAgainQuestion;
    }

    //Will be called 1 time
    public String generateRateRecommendQuestion(String brandLogoUrl, String competitor1LogoUrl, String competitor2LogoUrl) {

        String rateRecommendQuestion = "";

        rateRecommendQuestion = rateRecommend.replace("<BRAND_LOGO_URL>", brandLogoUrl);
        rateRecommendQuestion = rateRecommendQuestion.replace("<COMPETITOR_1_LOGO_URL>", competitor1LogoUrl);
        rateRecommendQuestion = rateRecommendQuestion.replace("<COMPETITOR_2_LOGO_URL>", competitor2LogoUrl);

        return rateRecommendQuestion;
    }

    //Will be called 1 time
    public String generateRateInfluencerQuestion() {

        String rateInfluencerQuestion = "";

        rateInfluencerQuestion = rateInfluencer;

        return rateInfluencerQuestion;
    }

    //Will be called 1 time
    public String generateSubInfluencerQuestion() {

        String subInfluencerQuestion = "";

        subInfluencerQuestion = subInfluencer;

        return subInfluencerQuestion;
    }

    //Will be called 1 time
    public String generateSubLikelyQuestion() {

        String subLikelyQuestion = "";

        subLikelyQuestion = subLikely;

        return subLikelyQuestion;
    }

    //Will be called 1 time
    public String generateSubGridCreativeEmotionQuestion() {

        String subGridCreativeEmotionQuestion = "";

        subGridCreativeEmotionQuestion = subGridCreativeEmotion;

        return subGridCreativeEmotionQuestion;
    }

}
