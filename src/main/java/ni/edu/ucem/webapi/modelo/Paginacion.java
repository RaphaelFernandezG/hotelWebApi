package ni.edu.ucem.webapi.modelo;

public class Paginacion 
{
    public static final int DEFAULT_OFFSET = 0;
    public static final int DEFAULT_LIMIT = 10;
    public static final String DEFAULT_FIELDS = "*";
    public static final String DEFAULT_SORT = "id";
    public static final String DEFAULT_SEARCH = "";
    public static final String DEFAULT_SORTORDER = "ASC";
    private final int limit;
    private final int offset;
    private final String fields;
    private final String sort;
    private final String search;
    private final String sortOrder;
    
    public static class Builder
    {
        private int offset;
        private int limit;
        private String fields;
        private String sort;
        private String search;
        private String sortOrder;
        
        public Builder(final int offset, final int limit,final String fields,
                final String sort,
                final String search,
                final String sortOrder)
        {
            this.offset = offset;
            this.limit = limit;
            this.fields = fields;
            this.sort = sort;
            this.search = search;
            this.sortOrder = sortOrder;
        }
        
        public Paginacion build() 
        {
            if (this.offset < 0 || this.limit < 1) 
            {
                this.offset = Paginacion.DEFAULT_OFFSET;
                this.limit = Paginacion.DEFAULT_LIMIT;
            } 
            if (this.fields.isEmpty())
            {
                this.fields = DEFAULT_FIELDS;
            }
            if (this.sort.isEmpty())
            {
                this.sort=DEFAULT_SORT;
            }
            /*if (this.search.isEmpty())
            {
                this.search=DEFAULT_SEARCH;
            }else
            {
                this.search=String.format("Where lower(nombre) like '%%%s%%'",this.search);
            }*/
            
            return new Paginacion(this.offset,this.limit,this.fields,this.sort,
                    this.search,this.sortOrder);
        }
    }

    public Paginacion(final int offset, final int limit,final String fields,
            final String sort,final String search,final String sortOrder)
    {
        this.offset = offset;
        this.limit = limit;
        this.fields = fields;
        this.sort = sort;
        this.search = search;
        this.sortOrder = sortOrder;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }
    
    public String getFields()
    {
        return fields;
    }
    public String getSort()
    {
        return sort;
    }
    public String getSearch()
    {
        return search;
    }
    public String getSortOrder()
    {
        return sortOrder;
    }
}
