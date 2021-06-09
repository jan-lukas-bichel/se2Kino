package de.uni_hamburg.informatik.swt.se2.kino.startup;

public interface Beobachter
{
    /**
     * Methode die auf Änderungen in den beobachtbaren Klassen reagiert
     * @param beobachtbar
     * @require beobachtbar != null
     *
     */
    public void reagiereAufAenderung(Beobachtbar beobachtbar);
}
