package ni.edu.ucem.webapi.modelo;

/**
 *
 * @author dbojorge
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;

public class Cupo<T> {
    
    @NotNull
    private String desde;
    
    private String hasta;
    private List<T> data;
    
    public Cupo(){
    }
    
    public Cupo(final String desde,final String hasta,final List<T> data) //throws ParseException
    {
        //SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm.sss'Z'");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        Date dsd,hst;
        try{
         dsd = format2.parse(desde);
         hst= format2.parse(hasta);
        }catch(Exception ex){
            dsd=new Date();
            hst = new Date();
            System.err.println(ex.getMessage());
        }
        //this.desde = desde;
        //this.hasta = hasta;
        this.desde = format1.format(dsd);
        this.hasta = format1.format(hst);
        if (dsd.compareTo(hst)<0)
        {
            this.data = data;
        }else{
            this.data = null;
        }
        
    }
    
    public List<T> getData()
    {
        return data;
    }
    
    public void setData(List<T> data)
    {
        this.data = data;
    }
    
    public String getDesde()
    {
        
        //final Date dsd = format2.parse(desde);
        //return form.format(desde);
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
    }
}
