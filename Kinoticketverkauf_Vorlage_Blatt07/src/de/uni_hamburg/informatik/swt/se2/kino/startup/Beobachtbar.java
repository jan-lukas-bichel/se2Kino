package de.uni_hamburg.informatik.swt.se2.kino.startup;

import java.util.HashSet;
import java.util.Set;

public abstract class Beobachtbar
{

    // Dieses Set Speichert die Beobachter die sich Registriert haben 
    private Set<Beobachter> _beobachter;

    public Beobachtbar()
    {
        _beobachter = new HashSet<Beobachter>();
    }

    /**
     * Fügt ein Beobachter dem Set hinzu
     * 
     * @param beobachter der dem Set hinzugefügt werden soll
     * @require beobachter != null
     */
    public void fuegeBeobachterHinzu(Beobachter beobachter)
    {
        assert beobachter != null : "Vorbedingung verletzt: beobachter ist null";
        _beobachter.add(beobachter);
    }

    /**
     * Informiert jeden Beobachter über Änderungen  
     */
    public void informiereUeberAenderung()
    {
        for (Beobachter beobachter : _beobachter)
        {
            beobachter.reagiereAufAenderung(this);
        }
    }
}
