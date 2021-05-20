import { createBrowserHistory } from "history";
import { Navigation } from "components/common";
import { Basket } from "components/basket";
import { Route, Router, Switch } from "react-router-dom";
import * as view from "views";
import * as ROUTES from "constants/routes";
import PublicRoute from "./PublicRoute";
import ClientRoute from "./ClientRoute";

import React from "react";

export const history = createBrowserHistory();

const AppRouter = () => (
  <Router history={history}>
    <>
      <Navigation />
      <Basket />
      <Switch>
        <Route component={view.Search} exact path={ROUTES.SEARCH} />
        {/* <Route component={view.Home} exact path={ROUTES.HOME} /> */}
        <Route component={view.Home} exact path={ROUTES.LAPTOP} />

        <Route component={view.ViewProduct} path={ROUTES.VIEW_PRODUCT} />
        <PublicRoute component={view.SignUp} path={ROUTES.SIGNUP} />
        <PublicRoute component={view.SignIn} exact path={ROUTES.SIGNIN} />

        <ClientRoute component={view.UserAccount} exact path={ROUTES.ACCOUNT} />
        <ClientRoute
          component={view.EditAccount}
          exact
          path={ROUTES.ACCOUNT_EDIT}
        />
      </Switch>
    </>
  </Router>
);

export default AppRouter;
