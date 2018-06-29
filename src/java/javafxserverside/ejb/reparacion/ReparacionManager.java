package javafxserverside.ejb.reparacion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import javafxserverside.ejb.reparacion.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javafxserverside.entity.Reparacion;
import javafxserverside.exception.reparacion.ReparacionCreateException;
import javafxserverside.exception.reparacion.ReparacionDataException;
import javafxserverside.exception.reparacion.ReparacionDeleteException;
import javafxserverside.exception.reparacion.ReparacionQueryException;
import javafxserverside.exception.reparacion.ReparacionUpdateException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Define EJB for Reparacion
 *
 * @author Carlos
 */
@Stateless
public class ReparacionManager implements ReparacionManagerLocal {

    private static final Logger logger = Logger.getLogger(ReparacionManager.class.getName());

    @PersistenceContext
    private EntityManager em;

    /**
     * Get all reparaciones
     *
     * @return reparaciones collection
     */
    @Override
    
    public List<Reparacion> getAllReparaciones() throws ReparacionQueryException {
        logger.info("ReparacionManager: Finding all reparaciones.");
        return em.createNamedQuery("getAllReparaciones").getResultList();
    }

    /**
     * Create new reparacion.
     *
     * @param reparacion new reparacion
     * @throws javafxserverside.exception.reparacion.ReparacionCreateException
     * @throws javafxserverside.exception.reparacion.ReparacionDataException
     */
    @Override
    public void createReparacion(Reparacion reparacion) throws ReparacionCreateException, ReparacionDataException {
        logger.info("Reparacion Manager: creating reparacion.");

        try {
            if (!isValid(reparacion)) {
                logger.info("ReparacionManager: ReparacionDataException creating reparacion.");
                throw new ReparacionDataException("Couldn't create reparacion, invalid reparacion data.");
            }
            em.persist(reparacion);
        } catch (Exception ex) {
            logger.info("ReparacionManager: ReparacionCreateException creating reparacion");
            throw new ReparacionCreateException("Error creating reparacion.\n" + ex.getMessage());
        }
        logger.info("Reparacion id: < " + reparacion.getId().toString() + " > created.");
    }

    /**
     * Get reparaciones by date range
     *
     * @return reparaciones collection
     */
    @XmlElement(name="reparaciones", type=Reparacion.class)
    @Override
    public List<Reparacion> getReparacionesByDate(String fromDate, String toDate) throws ReparacionQueryException {
        logger.info("Reparacion Manager: finding reparacion by date.");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date fd = null;
        Date td = null;
        
        try {

            fd = formatter.parse(fromDate);
            System.out.println(fd);
            System.out.println(formatter.format(fd));
            
            td = formatter.parse(toDate);
            System.out.println(td);
            System.out.println(formatter.format(td));
            

        } catch (ParseException e) {
            e.printStackTrace();
        }
        


        return em.createNamedQuery("getReparacionesByDate").setParameter("fromdate", new java.sql.Date(fd.getTime())).setParameter("todate", new java.sql.Date(td.getTime())).getResultList();
    }

    /**
     * Get Reparacion by associated Cliente.
     * @param id
     * @return Reparacion List
     * @throws ReparacionQueryException
     */
    @Override
    public List<Reparacion> getReparacionesByCliente(int id) throws ReparacionQueryException {
        logger.info("Reparacion Manager: finding reparacion by cliente.");

        return em.createNamedQuery("getReparacionesByCliente").setParameter("id", id).getResultList();
    }

    /**
     * Get reparacion by id number
     *
     * @param id id number
     * @return reparacion with matching id
     * @throws ReparacionQueryException
     */
    @Override
    public Reparacion getReparacionById(int id) throws ReparacionQueryException {
        logger.info("ReparacionManager: finding reparacion by id.");
        return em.find(Reparacion.class, id);
    }

    /**
     * Update reparacion values
     *
     * @param reparacion reparacion to update (Same id, new values)
     * @throws ReparacionUpdateException
     */
    @Override
    public void updateReparacion(Reparacion reparacion) throws ReparacionUpdateException {
        try {
            if (!em.contains(reparacion)&& isValid(reparacion)) {
                em.merge(reparacion);
            }
        } catch (Exception ex) {
            logger.info("ReparacionManager: ReparacionDataException updating reparacion.");
            throw new ReparacionUpdateException("Error updating reparacion.\n" + ex.getMessage());
        }
    }

    /**
     * Delete reparacion by id.
     *
     * @param id reparacion id
     */
    @Override
    public void deleteReparacion(Reparacion reparacion) throws ReparacionDeleteException {
        logger.info("ReparacionManager: Deleting user.");
        try {
            reparacion = em.merge(reparacion);
            em.remove(reparacion);
        } catch (Exception ex) {
            logger.severe("ReparacionManager: ReparacionDeleteException deleting reparacion.");
            throw new ReparacionDeleteException("Error deleting reparacion.\n" + ex.getMessage());
        }
        logger.info("Reparacion id: < " + reparacion.getId() + " > deleted.");
    }

    /**
     * Utility to check if reparacion data is valid
     *
     * @param f reparacion to check
     * @return true if valid
     */
    private boolean isValid(Reparacion f) {
        
        /** This validation allows null value for cliente (For Restful Test)
         *  
         **/
        if (f.getId() == null || f.getFechainicio() == null || f.getCoche() == null
                || f.getDescripcion() == null) {
            return false;
        } else {
            return true;
        }
    }
}
