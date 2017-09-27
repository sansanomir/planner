import { AmbulancePlannerPage } from './app.po';

describe('ambulance-planner App', function() {
  let page: AmbulancePlannerPage;

  beforeEach(() => {
    page = new AmbulancePlannerPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
