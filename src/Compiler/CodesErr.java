package Compiler;

public enum CodesErr {
	CAR_INC_ERR("Symbole inconnu"),
	FIC_VID_ERR("Erreur d'ouverture de fichier"),
	PROGRAMM_ERR("mot cles programme attendu ! "),
	CONST_ERR("mot cles const attendu ! "),
	THEN_ERR("mot cles then attendu !"),
	BEGIN_ERR("mot cles begin attendu !"),
	VAR_ERR("mot cles var attendu !"),
	ID_ERR("identificateur attendu !"),
	PVIR_ERR("symbole ; attendu !"),
	AFFEC_ERR("symbole := attendu !"),
	PNT_ERR("symbole . attendu !"), SUP_ERR("symbole > attendu !"),
	SUPEG_ERR("symbole >= attendu !"),
	DIFF_ERR("symbole != attendu !"),
	INF_ERR("symbole < attendu !"),
	INFEG_ERR("symbole <= attendu !"),
	EG_ERR("symbole = attendu !"),
	NUM_ERR("Numero attendu !"),
	WHILE_ERR("mot cles while attendu !"),
	WRITE_ERR("mot cles write attendu !"),
	IF_ERR("mot cles if attendu !"),
	READ_ERR("mot read attendu !"),
	PARG_ERR("symbole ( attendu !"),
	PARD_ERR("symbole ) attendu !"),
	VIR_ERR("symbole , attendu !"),
	DIV_ERR("symbole / attendu !"),
	MUL_ERR("symbole * attendu !"),
	MOINS_ERR("symbole - attendu !"),
	PLUS_ERR("symbole + attendu !"),
	END_ERR("mot cles end attendu !"),
	DO_ERR("mot cles do attendu !"),

	ID_ALREADY_FOUND_ERR("mot cles existe deja"),

	ID_NOT_DEFINED_ERR("mot cles n'est pas défini");
String Private_Message = null;

CodesErr(String err) {
	this.Private_Message = err;
	
	// TODO Auto-generated constructor stub
}

public String getPrivate_Message() {
	return Private_Message;
}

public void setPrivate_Message(String private_Message) {
	Private_Message = private_Message;
}
}
	