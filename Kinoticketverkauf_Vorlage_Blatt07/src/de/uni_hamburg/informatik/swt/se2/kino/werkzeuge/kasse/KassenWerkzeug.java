package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.kasse;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.uni_hamburg.informatik.swt.se2.kino.fachwerte.Datum;
import de.uni_hamburg.informatik.swt.se2.kino.materialien.Kino;
import de.uni_hamburg.informatik.swt.se2.kino.materialien.Tagesplan;
import de.uni_hamburg.informatik.swt.se2.kino.materialien.Vorstellung;
import de.uni_hamburg.informatik.swt.se2.kino.startup.Beobachtbar;
import de.uni_hamburg.informatik.swt.se2.kino.startup.Beobachter;
import de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.datumsauswaehler.DatumAuswaehlWerkzeug;
import de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.platzverkauf.PlatzVerkaufsWerkzeug;
import de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.vorstellungsauswaehler.VorstellungsAuswaehlWerkzeug;

/**
 * Das Kassenwerkzeug. Mit diesem Werkzeug kann die Benutzerin oder der Benutzer
 * eine Vorstellung auswählen und Karten für diese Vorstellung verkaufen und
 * stornieren.
 * 
 * @author SE2-Team
 * @version SoSe 2021
 */
public class KassenWerkzeug implements Beobachter
{
    // Das Material dieses Werkzeugs
    private Kino _kino;

    // UI dieses Werkzeugs
    private KassenWerkzeugUI _ui;

    // Die Subwerkzeuge
    private PlatzVerkaufsWerkzeug _platzVerkaufsWerkzeug;
    private DatumAuswaehlWerkzeug _datumAuswaehlWerkzeug;
    private VorstellungsAuswaehlWerkzeug _vorstellungAuswaehlWerkzeug;

    /**
     * Initialisiert das Kassenwerkzeug.
     * 
     * @param kino das Kino, mit dem das Werkzeug arbeitet.
     * 
     * @require kino != null
     */
    public KassenWerkzeug(Kino kino)
    {
        assert kino != null : "Vorbedingung verletzt: kino != null";

        _kino = kino;

        // Subwerkzeuge erstellen
        _platzVerkaufsWerkzeug = new PlatzVerkaufsWerkzeug();
        _datumAuswaehlWerkzeug = new DatumAuswaehlWerkzeug();
        _vorstellungAuswaehlWerkzeug = new VorstellungsAuswaehlWerkzeug();

        // UI erstellen (mit eingebetteten UIs der direkten Subwerkzeuge)
        _ui = new KassenWerkzeugUI(_platzVerkaufsWerkzeug.getUIPanel(),
                _datumAuswaehlWerkzeug.getUIPanel(),
                _vorstellungAuswaehlWerkzeug.getUIPanel());

        registriereUIAktionen();
        setzeTagesplanFuerAusgewaehltesDatum();
        setzeAusgewaehlteVorstellung();

        _ui.zeigeFenster();

        // Registriere Beobachter
        _datumAuswaehlWerkzeug.fuegeBeobachterHinzu(this);
        _vorstellungAuswaehlWerkzeug.fuegeBeobachterHinzu(this);
    }

    /**
     * Fügt die Funktionalitat zum Beenden-Button hinzu.
     */
    private void registriereUIAktionen()
    {
        _ui.getBeendenButton()
            .addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    reagiereAufBeendenButton();
                }
            });
    }

    /**
     * Setzt den in diesem Werkzeug angezeigten Tagesplan basierend auf dem derzeit
     * im DatumsAuswahlWerkzeug ausgewählten Datum.
     */
    private void setzeTagesplanFuerAusgewaehltesDatum()
    {
        Tagesplan tagesplan = _kino.getTagesplan(getAusgewaehltesDatum());
        _vorstellungAuswaehlWerkzeug.setTagesplan(tagesplan);
    }

    /**
     * Passt die Anzeige an, wenn eine andere Vorstellung gewählt wurde.
     */
    private void setzeAusgewaehlteVorstellung()
    {
        _platzVerkaufsWerkzeug.setVorstellung(getAusgewaehlteVorstellung());
    }

    /**
     * Beendet die Anwendung.
     */
    private void reagiereAufBeendenButton()
    {
        _ui.schliesseFenster();
    }

    /**
     * Gibt das derzeit gewählte Datum zurück.
     */
    private Datum getAusgewaehltesDatum()
    {
        return _datumAuswaehlWerkzeug.getSelektiertesDatum();
    }

    /**
     * Gibt die derzeit ausgewaehlte Vorstellung zurück. Wenn keine Vorstellung
     * ausgewählt ist, wird <code>null</code> zurückgegeben.
     */
    private Vorstellung getAusgewaehlteVorstellung()
    {
        return _vorstellungAuswaehlWerkzeug.getAusgewaehlteVorstellung();
    }

    @Override
    public void reagiereAufAenderung(Beobachtbar beobachtbar)
    {
        assert beobachtbar != null : "Vorbedingung verletzt: beobachtbar !=null";
        //wenn auf Änderung in _datumAuswaehlWerkzeug dann wird der Tagesplan ans Datum angepasst
        if (beobachtbar == _datumAuswaehlWerkzeug)
        {
            setzeTagesplanFuerAusgewaehltesDatum();

        }
        //wenn auf Änderung in _vorstellungAuswaehlWerkzeug dann wird die gewählte Vorstellung ausgewählt
        else if (beobachtbar == _vorstellungAuswaehlWerkzeug)
        {
            setzeAusgewaehlteVorstellung();
        }
    }
}
