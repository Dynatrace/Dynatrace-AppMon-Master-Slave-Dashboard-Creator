/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package masterslave.dashboard.creator;

import org.w3c.dom.Element;

/**
 *
 * @author cwuk-agardner
 */
public class Dashlet
{
    private Element m_oElement;
    public Dashlet(Element oElement)
    {
        m_oElement = oElement;
    }

    public String getID()
    {
        return m_oElement.getAttribute("id");
    }
    
    public Element getElement()
    {
        return m_oElement;
    }
    
    public String toString()
    {
        return m_oElement.getAttribute("name");
    }
}
