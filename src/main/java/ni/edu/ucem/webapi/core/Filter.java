package ni.edu.ucem.webapi.core;

public class Filter 
{
    private int limit;
    private int offset;
    
    public Filter()
    {
    }
    
    public int getLimit() {
        return limit;
    }
    public void setLimit(int limit) {
        this.limit = limit;
    }
    public int getOffset() {
        return offset;
    }
    public void setOffset(int offset) {
        this.offset = offset;
    }
}
