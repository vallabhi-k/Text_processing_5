package project5;

import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.*;

public class SuffixArray {
  
    public static class Suffix implements Comparable<Suffix> {
        int index;
        int rank;
        int next;
 
        public Suffix(int index, int rank, int next) {
            this.index = index;
            this.rank = rank;
            this.next = next;
        }
         
        public int compareTo(Suffix suffix) {
            if (rank != suffix.rank) {
                return Integer.compare(rank, suffix.rank);
            }
            return Integer.compare(next, suffix.next);
        }
    }
     
    public static ArrayList<Integer> construct(String text) {
        int n = text.length();
        Suffix[] suffixes = new Suffix[n];
         
        for (int i = 0; i < n; i++) {
            suffixes[i] = new Suffix(i, text.charAt(i) - '$', 0);
        }
        for (int i = 0; i < n; i++) {
            suffixes[i].next = (i + 1 < n ? suffixes[i + 1].rank : -1);
        }
 
        Arrays.sort(suffixes);
 
        int[] indexes = new int[n];
       
        for (int length = 4; length < 2 * n; length <<= 1) {
             
            int rank = 0, previousRank = suffixes[0].rank;
            suffixes[0].rank = rank;
            indexes[suffixes[0].index] = 0;
            for (int i = 1; i < n; i++) {
                if (suffixes[i].rank == previousRank &&
                    suffixes[i].next == suffixes[i - 1].next) {
                    previousRank = suffixes[i].rank;
                    suffixes[i].rank = rank;
                } else {
                    previousRank = suffixes[i].rank;
                    suffixes[i].rank = ++rank;
                }
                indexes[suffixes[i].index] = i;
            }
             
            for (int i = 0; i < n; i++) {
                int nextIndex = suffixes[i].index + length / 2;
                suffixes[i].next = nextIndex < n ?
                   suffixes[indexes[nextIndex]].rank : -1;
            }
             
            Arrays.sort(suffixes);
        }
        int[] suffixArray = new int[n];
        ArrayList<Integer> result = new ArrayList<>();
        
        for (int i = 0; i < n; i++) {
            suffixArray[i] = suffixes[i].index;
            result.add(suffixes[i].index);
        }
        
        return result;
    }
}
