import { createBrowserHistory } from "history";
import { Navigation } from "components/common";
import {ProductList} from 'components/product'
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
        <Route component={view.Home} exact path={ROUTES.HOME} />
        <Route component={view.Search} exact path={ROUTES.SEARCH} />
        <Route exact path={ROUTES.LAPTOP}>
          <view.ProductList category={8095}></view.ProductList>
        </Route>
        <Route exact path={ROUTES.PHONE}>
          <view.ProductList category={1789}></view.ProductList>
        </Route>
        <Route exact path={ROUTES.ELECTRICAL}>
          <view.ProductList category={1882}></view.ProductList>
        </Route>
        <Route exact path={ROUTES.RECOMMENDED_PRODUCTS}>
          <view.RecommendProduct/>
        </Route>

        <Route component={view.ViewProduct} path={ROUTES.VIEW_PRODUCT} />
        <PublicRoute component={view.SignUp} path={ROUTES.SIGNUP} />
        <PublicRoute component={view.SignIn} exact path={ROUTES.SIGNIN} />

        <ClientRoute component={view.UserAccount} exact path={ROUTES.ACCOUNT} />
        <ClientRoute
          component={view.EditAccount}
          exact
          path={ROUTES.ACCOUNT_EDIT}
        />
        <ClientRoute component={view.CheckOut} path={ROUTES.CHECKOUT} />
      </Switch>
    </>
  </Router>
);

export default AppRouter;
