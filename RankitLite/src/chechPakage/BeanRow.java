package chechPakage;


public class BeanRow {

   
	public int   idService;
    public String libelleService;
    public int    noteService;
    public Boolean vote;
    



	
    public BeanRow(int idService, String libelleService, int noteService,Boolean vote) {
		// TODO Auto-generated constructor stub
        super(); 
        this.idService = idService;
        this.libelleService = libelleService;
        this.noteService=noteService; 
        this.vote=vote;
        }





	public int getIdService() {
		return idService;
	}





	public void setIdService(int idService) {
		this.idService = idService;
	}





	public String getLibelleService() {
		return libelleService;
	}





	public void setLibelleService(String libelleService) {
		this.libelleService = libelleService;
	}





	public int getNoteService() {
		return noteService;
	}





	public void setNoteService(int noteService) {
		this.noteService = noteService;
	}





	public Boolean getVote() {
		return vote;
	}





	public void setVote(Boolean vote) {
		this.vote = vote;
	}




}
