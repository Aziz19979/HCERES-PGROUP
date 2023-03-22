package org.centrale.hceres.dto.csv;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.centrale.hceres.dto.csv.utils.*;
import org.centrale.hceres.items.Activity;
import org.centrale.hceres.items.Book;
import org.centrale.hceres.items.TypeActivityId;
import org.centrale.hceres.service.csv.LanguageCreatorCache;
import org.centrale.hceres.service.csv.util.SupportedCsvTemplate;
import org.centrale.hceres.util.RequestParser;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class CsvBook extends DependentCsv<Activity, Integer> {
    // important the read field of name id_activity isn't the same
    // id activity in activity.csv
    // to get the id activity use both key:
    // the type of activity and the specific count
    private Integer idCsvBook;
    private static final int ID_CSV_BOOK_ORDER = 0;

    private java.sql.Date publicationDate;
    private static final int PUBLICATION_DATE_ORDER = 1;
    private String title;
    private static final int TITLE_ORDER = 2;
    private String editor;
    private static final int EDITOR_ORDER = 3;
    private String authors;
    private static final int AUTHORS_ORDER = 4;
    private String language;
    private static final int LANGUAGE_ORDER = 5;


    // dependency element
    private CsvActivity csvActivity;
    private Map<Integer, CsvActivity> activityIdCsvMap;
    private LanguageCreatorCache languageCreatorCache;

    public CsvBook(Map<Integer, CsvActivity> activityIdCsvMap, LanguageCreatorCache languageCreatorCache) {
        this.activityIdCsvMap = activityIdCsvMap;
        this.languageCreatorCache = languageCreatorCache;
    }

    @Override
    public void fillCsvDataWithoutDependency(List<?> csvData) throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvParseException(ID_CSV_BOOK_ORDER,
                        f -> this.setIdCsvBook(RequestParser.getAsInteger(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(PUBLICATION_DATE_ORDER,
                        f -> this.setPublicationDate(RequestParser.getAsDateCsvFormat(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(TITLE_ORDER,
                        f -> this.setTitle(RequestParser.getAsString(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(EDITOR_ORDER,
                        f -> this.setEditor(RequestParser.getAsString(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(AUTHORS_ORDER,
                        f -> this.setAuthors(RequestParser.getAsString(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(LANGUAGE_ORDER,
                        f -> this.setLanguage(RequestParser.getAsString(csvData.get(f))))
        );
    }

    @Override
    public void initializeDependencies() throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvDependencyException(ID_CSV_BOOK_ORDER,
                        this.getIdCsvBook(),
                        SupportedCsvTemplate.ACTIVITY,
                        this.activityIdCsvMap.get(this.getIdCsvBook()),
                        this::setCsvActivity)
        );
    }

    @Override
    public Activity convertToEntity() {
        Activity activity = this.csvActivity.convertToEntity();
        activity.setIdTypeActivity(TypeActivityId.BOOK.getId());

        Book book = new Book();
        book.setPublicationDate(this.getPublicationDate());
        book.setTitle(this.getTitle());
        book.setEditor(this.getEditor());
        book.setAuthors(this.getAuthors());
        book.setLanguage(this.languageCreatorCache.getOrCreateLanguage(this.getLanguage()));
        book.setLanguageId(this.languageCreatorCache.getOrCreateLanguage(this.getLanguage()).getLanguageId());
        book.setActivity(activity);

        activity.setBook(book);
        return activity;
    }


    @Override
    public String getMergingKey() {
        return (this.getCsvActivity().getCsvResearcher().getIdDatabase()
                + "_" + this.getPublicationDate()
                + "_" + this.getTitle()
                + "_" + this.getEditor()
                + "_" + this.getAuthors()
                + "_" + this.getLanguage()).toLowerCase();
    }

    @Override
    public String getMergingKey(Activity entity) {
        return (entity.getResearcherList().get(0).getResearcherId()
                + "_" + entity.getBook().getPublicationDate()
                + "_" + entity.getBook().getTitle()
                + "_" + entity.getBook().getEditor()
                + "_" + entity.getBook().getAuthors()
                + "_" + entity.getBook().getLanguage().getLanguageName()).toLowerCase();
    }

    @Override
    public void setIdDatabaseFromEntity(Activity entity) {
        this.setIdDatabase(entity.getIdActivity());
    }

    @Override
    public Integer getIdCsv() {
        return this.getIdCsvBook();
    }
}
