package ni.edu.ucem.webapi.serviceImpl;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ni.edu.ucem.webapi.dao.CategoriaCuartoDAO;
import ni.edu.ucem.webapi.dao.CuartoDAO;
import ni.edu.ucem.webapi.dao.ReservaDAO;
import ni.edu.ucem.webapi.modelo.CategoriaCuarto;
import ni.edu.ucem.webapi.modelo.Cuarto;
import ni.edu.ucem.webapi.modelo.Cupo;
import ni.edu.ucem.webapi.modelo.Huesped;
//import ni.edu.ucem.webapi.modelo.Cupo;
import ni.edu.ucem.webapi.modelo.Pagina;
import ni.edu.ucem.webapi.modelo.Paginacion;
import ni.edu.ucem.webapi.modelo.Reserva;
import ni.edu.ucem.webapi.service.InventarioService;

@Service
public class InventarioServiceImpl implements InventarioService {

    private final CategoriaCuartoDAO categoriaCuartoDAO;
    private final CuartoDAO cuartoDAO;
    private final ReservaDAO reservaDAO;

    public InventarioServiceImpl(final CategoriaCuartoDAO categoriaCuartoDAO,
            final CuartoDAO cuartoDAO,
            final ReservaDAO reservaDAO) {
        this.categoriaCuartoDAO = categoriaCuartoDAO;
        this.cuartoDAO = cuartoDAO;
        this.reservaDAO = reservaDAO;
    }

    @Transactional
    @Override
    public void agregarCategoriaCuarto(final CategoriaCuarto pCategoriaCuarto) {
        this.categoriaCuartoDAO.agregar(pCategoriaCuarto);
    }

    @Transactional
    @Override
    public void guardarCategoriaCuarto(final CategoriaCuarto pCategoriaCuarto) {
        if (pCategoriaCuarto.getId() < 1) {
            throw new IllegalArgumentException("La categoría del cuarto no existe");
        }
        this.categoriaCuartoDAO.guardar(pCategoriaCuarto);
    }

    @Transactional
    @Override
    public void eliminarCategoriaCuarto(final int pId) {
        if (pId < 1) {
            throw new IllegalArgumentException("ID invalido. Debe ser mayor a cero");
        }
        this.categoriaCuartoDAO.eliminar(pId);
    }

    @Override
    public CategoriaCuarto obtenerCategoriaCuarto(final int pId,final String fields) {
        return this.categoriaCuartoDAO.obtenerPorId(pId,fields);
    }

    @Override
    public Pagina<CategoriaCuarto> obtenerTodosCategoriaCuartos(Paginacion paginacion) {
        /*return this.categoriaCuartoDAO.obtenerTodos(paginacion.getOffset(),
                    paginacion.getLimit());
         */
        List<CategoriaCuarto> categoriacuartos;
        int count = this.categoriaCuartoDAO.contar();
        if (count > 0) {
            categoriacuartos = this.categoriaCuartoDAO.obtenerTodos(paginacion.getOffset(),
                    paginacion.getLimit(), paginacion.getFields(), paginacion.getSort(),
                    paginacion.getSearch(), paginacion.getSortOrder());
        } else {
            categoriacuartos = new ArrayList<CategoriaCuarto>();
        }
        //System.out.println(categoriacuartos.size());
        count = categoriacuartos.size();
        return new Pagina<CategoriaCuarto>(categoriacuartos, count, paginacion.getOffset(), paginacion.getLimit(),
                paginacion.getFields(), paginacion.getSort(), paginacion.getSearch());
    }

    @Override
    public Pagina<CategoriaCuarto> obtenerFiltroCagetoriaCuartos(String nombre, String descripcion, Paginacion paginacion) {
        List<CategoriaCuarto> categorias = null;

        nombre = nombre == null ? "" : nombre;
        descripcion = descripcion == null ? "" : descripcion;

        final int count = this.categoriaCuartoDAO.contarFiltro(nombre, descripcion);
        //acá acá acá filtrar
        if (count > 0) {
            categorias = this.categoriaCuartoDAO.obtenerFiltroCagetoriaCuartos(nombre, descripcion, paginacion.getOffset(), paginacion.getLimit(),
                    paginacion.getFields(), paginacion.getSort(), paginacion.getSortOrder());
        } else {
            categorias = new ArrayList<CategoriaCuarto>();
        }
        //int count = this.categoriaCuartoDAO.contar();

        //final int count = categorias.size();
        return new Pagina<CategoriaCuarto>(categorias, count, paginacion.getOffset(), paginacion.getLimit(),
                paginacion.getFields(), paginacion.getSort(), paginacion.getSearch());
    }

    @Transactional
    @Override
    public void agregarCuarto(final Cuarto pCuarto) {
        this.cuartoDAO.agregar(pCuarto);

    }

    @Transactional
    @Override
    public void guardarCuarto(final Cuarto pCuarto) {
        if (pCuarto.getId() < 1) {
            throw new IllegalArgumentException("El cuarto no existe");
        }
        this.cuartoDAO.guardar(pCuarto);
    }

    @Transactional
    @Override
    public void eliminarCuarto(final int pId) {
        if (pId < 1) {
            throw new IllegalArgumentException("ID invalido. Debe ser mayor a cero");
        }
        this.cuartoDAO.eliminar(pId);
    }

    @Override
    public Cuarto obtenerCuarto(final int pId,final String pFields) {
        if (pId < 0) {
            throw new IllegalArgumentException("ID inválido. debe ser mayor a cero.");
        }
        return this.cuartoDAO.obtenerPorId(pId,pFields);
    }

    @Override
    public Pagina<Cuarto> obtenerTodosCuarto(Paginacion paginacion) {
        List<Cuarto> cuartos;
        final int count = this.cuartoDAO.contar();
        if (count > 0) {
            cuartos = this.cuartoDAO.obtenerTodos(paginacion.getOffset(),
                    paginacion.getLimit(), paginacion.getFields(), paginacion.getSort(),
                    paginacion.getSearch(), paginacion.getSortOrder());
        } else {
            cuartos = new ArrayList<Cuarto>();
        }
        return new Pagina<Cuarto>(cuartos, count, paginacion.getOffset(), paginacion.getLimit(),
                paginacion.getFields(), paginacion.getSort(), paginacion.getSearch());
    }

    @Override
    public Pagina<Cuarto> obtenerTodosCuartoEnCategoria(final int pCategoriaCuartoId, final Paginacion paginacion) {
        final int count = this.cuartoDAO.contarPorCategoria(pCategoriaCuartoId);
        List<Cuarto> cuartos = null;
        if (count > 0) {
            cuartos = this.cuartoDAO.obtenerTodosPorCategoriaId(pCategoriaCuartoId, paginacion.getOffset(),
                    paginacion.getLimit());
        } else {
            cuartos = new ArrayList<Cuarto>();
        }
        return new Pagina<Cuarto>(cuartos, count, paginacion.getOffset(), paginacion.getLimit(),
                paginacion.getFields(), paginacion.getSort(), paginacion.getSearch());
    }

    //Cupo
    @Override
    public Cupo<Cuarto> obtenerCupo(final String desde, final String hasta, final int categoria, final Paginacion paginacion) {
        List<Cuarto> cuartos = null;
        /*SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Date dsd = new Date(),hst = new Date();
        //try {
            //dsd = formatter.parse(desde.replaceAll("Z$", "+0000"));
            //hst = formatter.parse(hasta.replaceAll("Z$", "+0000"));
            dsd = formatter.format(desde);
        /*} catch (ParseException ex) {
            Logger.getLogger(InventarioServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            //dsd = null;
            //hst = null;
        }*/
        cuartos = this.cuartoDAO.consultaCupo(desde, hasta, categoria, paginacion.getOffset(), paginacion.getLimit());
        return new Cupo<Cuarto>(desde, hasta, cuartos);
    }

    @Override
    public Cuarto obtenerCuartoReservacion(int id) {
        //Reservacion reserva = null;
        Cuarto cuarto = null;
        int cantidad = this.cuartoDAO.contarCuartosReserva(id);

        if (cantidad > 0) {
            cuarto = this.cuartoDAO.getCuartoReserva(id);
            //reserva = new Reservacion()
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return cuarto;
    }

    @Override
    public Reserva obtenerReserva(int id) {
        return this.reservaDAO.obtenerReserva(id);
    }

    @Override
    public Huesped obtenerHuespedReserva(int id) {
        return this.reservaDAO.obtenerHuespedReserva(id);
    }

    @Override
    public void guardarReserva(Reserva reserva) {
        this.reservaDAO.grabarReserva(reserva);
    }

    @Override
    public Huesped obtenerHuesped(int pId) {
        return this.reservaDAO.obtenerHuesped(pId);
    }

    @Override
    public Reserva consultaReserva(String desde, String hasta, int cuarto) {
        return this.reservaDAO.consultaReserva(desde, hasta, cuarto);
    }

    @Override
    public String ValidarCampos(String pInput, String[] pCampos,final String pDefault) {
        for (Field f : CategoriaCuarto.class.getDeclaredFields() ) {
            //System.out.println("Valorar esto: " + f.getName());
          }
        
        //Boolean r;
        String salida;
        System.out.println("pIput=" + pInput);
        if (pInput.equals("*") || pInput.equals("id")) {
            //r = true;
            salida = pInput;
        } else {
            //r = true;
            /*Lista de Campos:  id,nombre,descripcion,precio*/
            String[] listaCampos = pCampos;
            String[] listaFields = pInput.split(",");
            //System.out.println(String.join(",",listaFields));
            ArrayList<String> listaVerificada = new ArrayList<String>();
            for (int x = 0; x < listaFields.length; x++) {
                //System.out.println("Validar Campos: " + listaFields[x]);
                /*if (!Arrays.asList(listaCampos).contains(listaFields[x])) {
                    r = false;
                }*/
                if (Arrays.asList(listaCampos).contains(listaFields[x])) {
                    listaVerificada.add(listaFields[x]);
                }
            }
            salida = String.join(",",listaVerificada);
        }
        if(salida.isEmpty())
            salida = pDefault;
        
        //System.out.println("Salida: " + salida);
        return salida;
    }
    
    @Override
    public String ValidarCampos(String pInput, Class pObj,final String pDefault) {
        ArrayList<String> listaCmp = new ArrayList<String>();
        //for (Field f : pObj.class.getDeclaredFields() ) {
        for (Field f : pObj.getDeclaredFields() ) {
            listaCmp.add(f.getName());
          }
        
        String[] listaCampos = listaCmp.toArray(new String[listaCmp.size()]);
        
        String salida;
        //System.out.println("pIput=" + pInput);
        if (pInput.equals("*") || pInput.equals("id")) {
            salida = pInput;
        } else {
            String[] listaFields = pInput.split(",");
            //System.out.println(String.join(",",listaFields));
            ArrayList<String> listaVerificada = new ArrayList<String>();
            for (int x = 0; x < listaFields.length; x++) {
                if (Arrays.asList(listaCampos).contains(listaFields[x])) {
                    listaVerificada.add(listaFields[x]);
                }
            }
            salida = String.join(",",listaVerificada);
        }
        if(salida.isEmpty())
            salida = pDefault;
        
        //System.out.println("Salida: " + salida);
        return salida;
    }
}
