/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.sistema;

import java.util.UUID;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.mindmap.DefaultMindmapNode;
import org.primefaces.model.mindmap.MindmapNode;

/**
 *
 * @author Carlos
 */
@ManagedBean
@ViewScoped
public class IndexSistemaMb {

    private MindmapNode root;

    private MindmapNode selectedNode;

    public IndexSistemaMb() {
        root = new DefaultMindmapNode("Factura", "Google WebSite", "FFCC00", false);

        MindmapNode ips = new DefaultMindmapNode("Inventario", "IP Numbers", "6e9ebf", true);
        MindmapNode ns = new DefaultMindmapNode("Presupuestos", "Namespaces", "6e9ebf", true);
        MindmapNode malware = new DefaultMindmapNode("Academico", "Malicious Software", "6e9ebf", true);

        root.addNode(ips);
        root.addNode(ns);
        root.addNode(malware);
    }

    public MindmapNode getRoot() {
        return root;
    }

    public MindmapNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(MindmapNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public void onNodeSelect(SelectEvent event) {
        MindmapNode node = (MindmapNode) event.getObject();

        //populate if not already loaded
        if (node.getChildren().isEmpty()) {
            Object label = node.getLabel();

            if (label.equals("NS(s)")) {
                for (int i = 0; i < 25; i++) {
                    node.addNode(new DefaultMindmapNode("ns" + i + ".google.com", "Namespace " + i + " of Google", "82c542", false));
                }
            } else if (label.equals("IPs")) {
                for (int i = 0; i < 18; i++) {
                    node.addNode(new DefaultMindmapNode("1.1.1." + i, "IP Number: 1.1.1." + i, "fce24f", false));
                }
            } else if (label.equals("Malware")) {
                for (int i = 0; i < 18; i++) {
                    String random = UUID.randomUUID().toString();
                    node.addNode(new DefaultMindmapNode("Malware-" + random, "Malicious Software: " + random, "3399ff", false));
                }
            }
        }
    }

    public void onNodeDblselect(SelectEvent event) {
        this.selectedNode = (MindmapNode) event.getObject();
    }
}
