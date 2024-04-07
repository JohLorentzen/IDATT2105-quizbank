
describe('Playing a quiz view', () => {
    beforeEach(() => {
      cy.window().then((win) => {
        win.localStorage.setItem('token', 'example-token-value');
        win.localStorage.setItem('username', 'e2e-username');
      });
  
      cy.intercept('GET', '/rest/quiz', { fixture: 'quizzes.json' }).as('getQuizzes');
      cy.intercept('GET', '/rest/quiz/categories', { fixture: 'categories.json' }).as('getCategories');

      
      cy.visit('/quiz');
    });
  
    it('should have a token in local storage', () => {
      cy.window().should((win) => {
        expect(win.localStorage.getItem('token')).to.eq('example-token-value');
      });
    });
  
    it('should show the quiz view', () => {
      cy.contains('h1', 'Available quizzes');
    });

    it('should play the quizbank project quiz', () => {
        cy.get('div.quiz-card').contains('Quizbank project').click();
        cy.contains('h2.quiz-header', 'Quizbank project');
    });

    it('Play the first quiz', () => {
        cy.get('div.quiz-card').contains('Quizbank project').click();
        cy.contains('h2.quiz-header', 'Quizbank project');
        cy.get('p.problem-statement').contains('Who is Johannes?');
        cy.get('input.text-answer-input').type('Lorentzen');
        cy.get('button.submit-button').click();
        cy.contains('p.problem-statement', 'Where is Sondre?');
        cy.get('input.text-answer-input').type('Hemsedal');
        cy.get('button.submit-button').click();
        cy.contains('p.problem-statement', 'What is Jens doing?');
        cy.get('input.text-answer-input').type('Coding');
        cy.get('button.submit-button').click();
        cy.get('button.check-results-button').click();
        cy.contains('p.score-summary', 'You got 3 / 3 correct answers');
    });
  });
  