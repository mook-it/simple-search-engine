/**
 * Created by S. Stefani on 2016-11-27.
 */

import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Document;
import se.kth.id1020.util.Word;
import java.util.Comparator;

public class Posting implements Comparable<Posting> {
    private String postingName;
    private Document document;
    private int occurrence;
    private int popularity;
    private int hits;

    public Posting(Word word, Attributes attributes) {
        this.postingName = attributes.document.name;
        this.document = attributes.document;
        this.popularity = attributes.document.popularity;
        this.occurrence = attributes.occurrence;
        this.hits = 1;
    }

    public void addDoc(Posting posting) {
        if (posting.getOccurrence() < this.occurrence) {
            this.occurrence = posting.getOccurrence();
        }
        this.hits++;
    }

    public int getOccurrence() {
        return occurrence;
    }

    public String getPostingName() {
        return postingName;
    }

    public int getHits() {
        return hits;
    }

    public Document getDocument() {
        return document;
    }

    public int compareTo(Posting posting) {
        return this.postingName.compareTo(posting.getPostingName());
    }

    public static class PostingComparator implements Comparator<Posting> {
        private String property;
        private boolean direction;

        public PostingComparator(String property, boolean direction) {
            this.property = property;
            this.direction = direction;
        }

        public void setProperty(String property) {
            this.property = property;
        }

        public void setDirection(boolean direction) {
            this.direction = direction;
        }


        public boolean isGreaterThan(Posting pst1, Posting pst2) {
            return compare(pst1, pst2) > 0 ? true : false;
        }

        public int compare(Posting pst1, Posting pst2) {
            if (property.equals("popularity"))
                return byPopularity(pst1, pst2);
            else if (property.equals("relevance"))
                return relevance(pst1, pst2);
            else if (property.equals("occurrence"))
                return byOccurrence(pst1, pst2);
            else
                return 0;
        }

        private int byOccurrence(Posting pst1, Posting pst2) {
            int diff = pst1.occurrence - pst2.occurrence;
            diff = direction ? diff : -diff;

            if (diff < 0) { return -1; }
            else if (diff > 0) { return 1; }
            else { return 0; }
        }

        private int byPopularity(Posting pst1, Posting pst2) {
            int diff = pst1.popularity - pst2.popularity;
            diff = direction ? diff : -diff;

            if (diff < 0) { return -1; }
            else if (diff > 0) { return 1; }
            else { return 0; }
        }

        private int relevance(Posting pst1, Posting pst2) {
            int diff = pst1.getHits() - pst2.getHits();
            diff = direction ? diff : -diff;

            if (pst1.getPostingName().equals(pst2.getPostingName())) { return 0; }
            if (diff > 0) { return 1; }
            else if (diff < 0) { return -1; }
            else { return 1; }
        }
    }
}