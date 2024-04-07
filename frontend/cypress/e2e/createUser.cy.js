describe('A new user to Quizbank', () => {
    beforeEach(() => {
        cy.visit('/');
    });
    it('should show the landing page', () => {
        cy.contains('h1', 'What is Quizbank');
        cy.contains('button', 'Sign up now'); 
    });

    it ('should send user to /quizes with a popup to remind them to login or sign up', () => {
        cy.contains('button', 'Sign up now').click();
        cy.url().should('include', '/login');
        cy.get('a').contains('Create an account').click();
        cy.get('input#username').type('e2e-username');
        cy.get('input#password').type('e2e-password');
        cy.intercept('POST', '/user/register', {
            statusCode: 201, body: { token: 'example' } }
        );
        cy.get('button.login-btn').click();
        cy.url().should('include', '/quiz');
    });
});