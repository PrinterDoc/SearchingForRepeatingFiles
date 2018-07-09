import java.util.ArrayList;

public  class DescribePart{
    /**
     Class using for describing part which repeat in file
     *
     */
     String fileName;
     long repeatCount;
     String repeatPart;


    public DescribePart(String repeatPart, long repeatCount, String fileName ){
        this.fileName = fileName;
        this.repeatPart = repeatPart;
        this.repeatCount = repeatCount;
    }
//

    public void incrCount(long increase){
        repeatCount += increase;
    }
}