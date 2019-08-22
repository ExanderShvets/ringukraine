package utils.verify;

class VerificationError extends Error {
    private static final long serialVersionUID = 8247563849457669512L;

    VerificationError(String message) {
        super(message);
    }
}
