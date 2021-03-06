/**
 * ResultDocument.java
 *
 * Created by S. Stefani on 2017-01-06.
 */

import se.kth.id1020.util.Document;

import java.util.Comparator;
import java.util.HashMap;

public class ResultDocument implements Comparable<ResultDocument> {
    private Document document;
    private int hits;
    private int popularity;
    private double relevance;

    public ResultDocument(Document document, int hits) {
        this.document = document;
        this.hits = hits;
        this.popularity = document.popularity;
    }

    public ResultDocument(Document document, double relevance) {
        this.relevance = relevance;
        this.document = document;
        this.popularity = document.popularity;
    }

    /**
     * Compute the relevance of posting respect to the executed query by means
     * of tf-idf.
     *
     * @param documentsLengths contains the lengths of all the documents
     * @param relevantDocs is the number of relevant docs for the query
     */
    public void computeRelevance(HashMap<String, Integer> documentsLengths, int relevantDocs) {
        relevance = tf(documentsLengths.get(document.name)) * idf(documentsLengths.size(), relevantDocs);
    }

    // Compute tf
    private double tf(int totalTerms) {
        return (double) this.hits / totalTerms;
    }

    // Compute idf
    private double idf(int totalDocs, int relevantDocs) {
        return Math.log10((double) totalDocs / (double) relevantDocs);
    }

    // Increment the number of hits
    public void updatePosting() {
        this.hits++;
    }

    public Document getDocument() {
        return document;
    }

    public int getHits() {
        return hits;
    }

    public int getPopularity() {
        return popularity;
    }

    public double getRelevance() {
        return relevance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResultDocument that = (ResultDocument) o;

        return this.document.name.equals(that.document.name);
    }

    public int compareTo(ResultDocument o) {
        return this.document.name.compareTo(o.document.name);
    }

    public static class PopularityComparator implements Comparator<ResultDocument> {
        private int direction;

        public PopularityComparator(int direction) {
            this.direction = direction;
        }

        public int compare(ResultDocument o1, ResultDocument o2) {
            if (o1.getPopularity() < o2.getPopularity()) return -1 * direction;
            if (o1.getPopularity() > o2.getPopularity()) return direction;
            return 0;
        }
    }

    public static class RelevanceComparator implements Comparator<ResultDocument> {
        private int direction;

        public RelevanceComparator(int direction) {
            this.direction = direction;
        }

        public int compare(ResultDocument o1, ResultDocument o2) {
            if (o1.getRelevance() < o2.getRelevance()) return -1 * direction;
            if (o1.getRelevance() > o2.getRelevance()) return direction;
            return 0;
        }
    }
}