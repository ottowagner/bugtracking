Fehlerverfolgungssystem
(Schwierigkeitsgrad niedrig, Frontend Angular JS)
Es soll ein System implementiert und bereitgestellt werden, das eine einfache Erfassung und
Verfolgung von Fehlern in der Entwicklung eines Softwaresystem unterstützt. Fehler können von
Benutzern eingestellt und beschrieben werden und durchlaufen anschließend einen Prozess, der
letztendlich entweder zur Beseitigung des Fehlers oder zu einer Zurückweisung des erfassten Fehlers
führt.
Funktionale Anforderungen
Folgende funktionale Anforderungen muss das System erfüllen:
1. Es gibt einen Registrierungsdialog, über den sich neue Entwickler im System registrieren
können. Jeder Entwickler hat dabei einen vollständigen Namen, eine Email-Adresse und ein
Passwort. Er wird durch eine eindeutige Benutzerkennung identifiziert.
2. Jeder Entwickler muss sich vor der Benutzung des Systems mit seiner Benutzerkennung und
dem Passwort anmelden.
3. Ein Fehler kann einen der folgenden Zustände haben:
a. Anlegt
b. In Bearbeitung
c. Behoben
d. Abgelehnt
e. Wiedereröffnet
f. Geschlossen
4. Jeder im System angemeldete Entwickler kann einen neuen Fehlerbericht im System anlegen.
Dabei wird neben einer kurzen Überschrift auch eine textbasierte Beschreibung der
Fehlersituation vom Benutzer angegeben. Das System weist dem Fehler eine eindeutige
Fehlernummer zu. Der Fehler bekommt initial den Status „Angelegt“. Der Ersteller des
Fehlerberichts wird als Autor hinterlegt, das aktuelle Datum und die Uhrzeit werden
ebenfalls erfasst.
5. Ein Entwickler kann einen Fehler mit Status „Angelegt“ oder „Wiedereröffnet“ auswählen
und die Bearbeitung des Fehlers starten. Der Zustand wird auf „In Bearbeitung“ gesetzt und
der Entwickler als Bearbeiter hinterlegt. Ein Fehler kann immer nur von einem Entwickler
bearbeitet werden.
6. Der Bearbeiter eines Fehlers kann diesen in den Zustand „Behoben“ oder „Abgelehnt“
setzen. Die Bearbeitung ist damit abgeschlossen.
7. Der Autor des Fehlerberichts kann einen Fehlerbericht mit Status „Behoben“ oder
„Abgelehnt“ in die Folgezustände „Wiedereröffnet“ oder „Geschlossen“ setzen.
8. Fehlerberichte mit Status „Geschlossen“ können nicht mehr bearbeitet werden.
9. Bei allen Zustandsänderungen können die jeweiligen Entwickler Kommentare hinterlegen,
die chronologisch aufsteigend im Fehlerbericht angezeigt werden. Der Name des Entwicklers
wird mit dem Kommentar angezeigt. Außerdem soll der Zeitpunkt der letzten Änderung des
Fehlerberichts festgehalten werden.
10. Das System soll eine tabellarische Übersicht mit den im System erfassten Fehlerberichten
anbieten. Aus dieser Sicht heraus sind die einzelnen Bearbeitungen möglich.
11. Optionale Anforderung: Die tabellarische Übersicht soll eine Sortierung nach verschiedenen
Kriterien (Fehlernummer, Datum der Erstellung, ..) ermöglichen.
12. Optionale Anforderung: Die tabellarische Übersicht soll eine Filtermöglichkeit anbieten, die
alle geschlossenen Fehlerberichte ausblendet.