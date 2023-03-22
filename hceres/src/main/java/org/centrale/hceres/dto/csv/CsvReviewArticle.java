package org.centrale.hceres.dto.csv;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.centrale.hceres.dto.csv.utils.*;
import org.centrale.hceres.items.Activity;
import org.centrale.hceres.items.Journal;
import org.centrale.hceres.items.ReviewArticle;
import org.centrale.hceres.items.TypeActivityId;
import org.centrale.hceres.service.csv.JournalCreatorCache;
import org.centrale.hceres.service.csv.util.SupportedCsvTemplate;
import org.centrale.hceres.util.RequestParseException;
import org.centrale.hceres.util.RequestParser;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class CsvReviewArticle extends DependentCsv<Activity, Integer> {
    // important the read field of name id_activity isn't the same
    // id activity in activity.csv
    // to get the id activity use both key:
    // the type of activity and the specific count
    private Integer idCsvReviewArticle;
    private static final int ID_CSV_REVIEW_ARTICLE_ORDER = 0;
    private Integer year;
    private static final int YEAR_ORDER = 1;
    private String nameJournal;
    private static final int NAME_JOURNAL_ORDER = 2;
    private Integer nbReviewedArticles;
    private static final int NB_REVIEWED_ARTICLES_ORDER = 3;
    private BigDecimal impactFactorJournal;
    private static final int IMPACT_FACTOR_JOURNAL_ORDER = 4;


    // dependency element
    private CsvActivity csvActivity;
    private Map<Integer, CsvActivity> activityIdCsvMap;

    private JournalCreatorCache journalCreatorCache;


    public CsvReviewArticle(Map<Integer, CsvActivity> activityIdCsvMap, JournalCreatorCache journalCreatorCache) {
        this.activityIdCsvMap = activityIdCsvMap;
        this.journalCreatorCache = journalCreatorCache;
    }

    @Override
    public void fillCsvDataWithoutDependency(List<?> csvData) throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvParseException(ID_CSV_REVIEW_ARTICLE_ORDER,
                        f -> this.setIdCsvReviewArticle(RequestParser.getAsInteger(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(YEAR_ORDER,
                        f -> this.setYear(RequestParser.getAsInteger(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(NAME_JOURNAL_ORDER,
                        f -> this.setNameJournal(RequestParser.getAsString(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(NB_REVIEWED_ARTICLES_ORDER,
                        f -> this.setNbReviewedArticles(RequestParser.getAsInteger(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(IMPACT_FACTOR_JOURNAL_ORDER,
                        f -> this.setImpactFactorJournal(RequestParser.getAsBigDecimal(csvData.get(f))))
        );
    }

    @Override
    public void initializeDependencies() throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvDependencyException(ID_CSV_REVIEW_ARTICLE_ORDER,
                        this.getIdCsvReviewArticle(),
                        SupportedCsvTemplate.ACTIVITY,
                        this.activityIdCsvMap.get(this.getIdCsvReviewArticle()),
                        this::setCsvActivity)
        );
    }

    @Override
    public Activity convertToEntity() {
        Activity activity = this.getCsvActivity().convertToEntity();
        activity.setIdTypeActivity(TypeActivityId.REVIEWING_JOURNAL_ARTICLES.getId());
        ReviewArticle reviewArticle = new ReviewArticle();
        reviewArticle.setYear(this.getYear());
        reviewArticle.setImpactFactor(this.getImpactFactorJournal());
        reviewArticle.setNbReviewedArticles(this.getNbReviewedArticles());
        Journal journal = this.journalCreatorCache.getOrCreateJournal(this.getNameJournal());
        reviewArticle.setJournalId(journal.getJournalId());
        reviewArticle.setJournal(journal);
        activity.setReviewArticle(reviewArticle);
        reviewArticle.setActivity(activity);
        return activity;
    }

    @Override
    public String getMergingKey() {
        return (this.getCsvActivity().getCsvResearcher().getIdDatabase()
                + "_" + this.getYear()
                + "_" + this.getNameJournal()
                + "_" + this.getNbReviewedArticles()
                + "_" + this.getImpactFactorJournal()).toLowerCase();
    }

    @Override
    public String getMergingKey(Activity entity) {
        return (entity.getResearcherList().get(0).getResearcherId()
                + "_" + entity.getReviewArticle().getYear()
                + "_" + entity.getReviewArticle().getJournal().getJournalName()
                + "_" + entity.getReviewArticle().getNbReviewedArticles()
                + "_" + entity.getReviewArticle().getImpactFactor()).toLowerCase();
    }

    @Override
    public void setIdDatabaseFromEntity(Activity entity) {
        this.setIdDatabase(entity.getIdActivity());
    }

    @Override
    public Integer getIdCsv() {
        return this.getIdCsvReviewArticle();
    }
}
