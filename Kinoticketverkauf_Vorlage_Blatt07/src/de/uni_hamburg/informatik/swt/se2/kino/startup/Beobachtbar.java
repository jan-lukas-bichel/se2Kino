package de.uni_hamburg.informatik.swt.se2.kino.startup;

import java.util.HashSet;
import java.util.Set;

public abstract class Beobachtbar
{
    private Set<Beobachter> _beobachter;

    public Beobachtbar()
    {
        _beobachter = new HashSet<Beobachter>();
    }

    public void fuegeBeobachterHinzu(Beobachter beobachter)
    {
        _beobachter.add(beobachter);
    }

    public void informiereUeberDatumAenderung()
    {
        for (Beobachter beobachter : _beobachter)
        {
            beobachter.reagiereAufDatumAenderung();
        }
    }

    public void informiereUeberVorstellungAenderung()
    {
        for (Beobachter beobachter : _beobachter)
        {
            beobachter.reagiereAufVorstellungAenderung();
        }
    }
}
