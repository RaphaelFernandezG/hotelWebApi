package ni.edu.ucem.webapi.modelo;

import java.util.List;

public class Pagina<T>
{
    /*private String desde;
    private String hasta;*/
    private List<T> data;
    private int count;
    private int offset;
    private int limit;
    private String fields;
    private String sort;
    private String search;
    
    public Pagina(){
        
    }

    public Pagina(final List<T> data, final int count, final int offset,final int limit,
            final String fields,final String sort,final String search,
            final String desde,final String hasta)
    {
        this.data = data;
        this.count = count;
        this.offset = offset;
        this.limit = limit;
        this.fields = fields;
        this.sort = sort;
        this.search = search;
        /*this.desde = desde;
        this.hasta = hasta;*/
    }
    
    public Pagina(final List<T> data, final int count, final int offset,final int limit,
            final String fields,final String sort,final String search)
    {
        this.data = data;
        this.count = count;
        this.offset = offset;
        this.limit = limit;
        this.fields = fields;
        this.sort = sort;
        this.search = search;
    }

    /*public String getDesde()
    {
        return desde;
    }
    
    public void setDesde(String desde)
    {
        this.desde = desde;
    }
    
    public String getHasta()
    {
        return hasta;
    }
    
    public void setHasta(String hasta)
    {
        this.hasta = hasta;
    }*/
    
    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
    
    public String getFields()
    {
        return fields;
    }
    public void setFields(String fields)
    {
        this.fields = fields;
    }
    
    public String getSort()
    {
        return sort;
    }
    
    public void setSort(String sort)
    {
        this.sort = sort;
    }
    public String getSearch()
    {
        return this.search;
    }
    public void setSearch(String search)
    {
        this.search = search;
    }
}
