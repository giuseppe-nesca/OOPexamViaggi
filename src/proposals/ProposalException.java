package proposals;

@SuppressWarnings("serial")
public class ProposalException extends Exception {
    public ProposalException() {
        super("unspecified error");
    }
	public ProposalException(String reason) {
		super(reason);
	}
}
