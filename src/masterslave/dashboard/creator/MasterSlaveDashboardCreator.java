/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package masterslave.dashboard.creator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class MasterSlaveDashboardCreator
{
    private File m_oDashboard;
    private Document m_oDoc;
    
    public MasterSlaveDashboardCreator(File oFile)
    {
        m_oDashboard = oFile;
        
        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            m_oDoc = dBuilder.parse(m_oDashboard);
			
            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            m_oDoc.getDocumentElement().normalize();
        }
        catch (Exception e)
        {
            System.out.println("Exception caught parsing dashboard.");
        }
    }
    
    public List<Dashlet> getDashlets()
    {
        List<Dashlet> oReturnList = new ArrayList<Dashlet>();
        
        NodeList nList = m_oDoc.getElementsByTagName("portletconfig"); 
        
            // For each chart source node, set each variable.
            for (int iTemp = 0; iTemp < nList.getLength(); iTemp++)
            {
                Node oNode = nList.item(iTemp);

                Element oElement = (Element) oNode;
                // Add dashlet name to list
                oReturnList.add(new Dashlet(oElement));
            }
        
        return oReturnList;
    }
    
    public void processDashboard(Dashlet oSelectedDashlet, List<Dashlet> oAllDashlets)
    {
        //TODO - Set selected master and pass full list to set slaves
        List<Dashlet> oSlaves = new ArrayList<Dashlet>();
        for  (Dashlet oDashlet : oAllDashlets)
        {
            // If IDs don't match, it must be a slave.
            if (!oDashlet.getID().equals(oSelectedDashlet.getID())) oSlaves.add(oDashlet);
        }
        System.out.println("Slaves Size: " + oSlaves.size());
        
        // Set master dashlet ismaster="true"
        Element oElement = oSelectedDashlet.getElement();
        oElement.setAttribute("ismaster", "true");
        
        /* 
         * For each slave, add the node:
         * <masterreference portletid="id value of the portletconfig for master dashlet" />
         * Note: Must create within loop because it gets "consumed" when it's added, so can't reuse :(
         */
        for (Dashlet oSlave : oSlaves)
        {
            Element oMasterRef = m_oDoc.createElement("masterreference");
            oMasterRef.setAttribute("portletid", oSelectedDashlet.getID());
            
            Element oSlaveElement = oSlave.getElement();
            oSlaveElement.appendChild(oMasterRef);
        }

    }
    
    /*
    * Save the XML to disk
    */
    public boolean save()
    {
        
       try {
           // Save the new file in the old directory with a _clean.dashboard.xml ending
           String strPath = m_oDashboard.getParent()+"/"; // Represents directory. Add the trailing slash in anticipation of the save.

           String strOldName = m_oDashboard.getName();
           
           // Strip off old .dashboard.xml then rename ending.
           String strNewName = strOldName.substring(0,strOldName.indexOf(".dashboard.xml")) + "_master_slave.dashboard.xml";
        
        Transformer transformer = TransformerFactory.newInstance().newTransformer();

        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        Result output = new StreamResult(new File(strPath+strNewName));
        m_oDoc.setXmlStandalone(true);
        Source input = new DOMSource(m_oDoc);
        transformer.transform(input, output);
        } catch (TransformerException ex) {
            System.out.println("Save Exception");
            return false;
        }
       return true;
    }
}
