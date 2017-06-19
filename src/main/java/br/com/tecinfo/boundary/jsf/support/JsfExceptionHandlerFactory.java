package br.com.tecinfo.boundary.jsf.support;

import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

public class JsfExceptionHandlerFactory extends ExceptionHandlerFactory {
	private ExceptionHandlerFactory parent;

	public JsfExceptionHandlerFactory(ExceptionHandlerFactory parent) {
		this.parent = parent;
	}

	public ExceptionHandler getExceptionHandler() {
		ExceptionHandler result = this.parent.getExceptionHandler();
		result = new CustomExceptionHandler(result);
		return result;
	}

	public class CustomExceptionHandler extends ExceptionHandlerWrapper {

		private ExceptionHandler wrapped;

		public CustomExceptionHandler(ExceptionHandler wrapped) {
			this.wrapped = wrapped;

		}

		public ExceptionHandler getWrapped() {
			return this.wrapped;
		}

		public void handle() throws FacesException {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();

			Map<String, Object> requestMap = facesContext.getExternalContext().getRequestMap();

			ResourceBundle messages = getMessages(facesContext);

			Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
			while (i.hasNext()) {
				handle(facesContext, navigationHandler, requestMap, messages, i);
			}

			i = getHandledExceptionQueuedEvents().iterator();
			while (i.hasNext()) {
				handle(facesContext, navigationHandler, requestMap, messages, i);
			}

			getWrapped().handle();
		}

		protected void handle(FacesContext facesContext, NavigationHandler navigationHandler,
				Map<String, Object> requestMap, ResourceBundle messages, Iterator<ExceptionQueuedEvent> exceptions) {
			ExceptionQueuedEvent event = (ExceptionQueuedEvent) exceptions.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

			Throwable t = context.getException();

			// String viewId =
			// facesContext.getExternalContext().getRequestServletPath();

			if ((t instanceof ViewExpiredException)) {
				ViewExpiredException vee = (ViewExpiredException) t;
				requestMap.put("currentViewId", vee.getViewId());
				navigationHandler.handleNavigation(facesContext, null, "login.xhtml");
				facesContext.renderResponse();
				exceptions.remove();
			}
		}

		protected ResourceBundle getMessages(FacesContext facesContext) {
			return facesContext.getApplication().getResourceBundle(facesContext, "messages");
		}
	}
}