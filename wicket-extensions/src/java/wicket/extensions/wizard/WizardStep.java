/*
 * $Id: org.eclipse.jdt.ui.prefs 5004 2006-03-17 20:47:08 -0800 (Fri, 17 Mar
 * 2006) eelco12 $ $Revision: 5004 $ $Date: 2006-03-17 20:47:08 -0800 (Fri, 17
 * Mar 2006) $
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.extensions.wizard;

import wicket.Component;
import wicket.MarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.markup.html.panel.Panel;
import wicket.model.AbstractReadOnlyModel;
import wicket.model.CompoundPropertyModel;
import wicket.model.IModel;
import wicket.model.Model;

/**
 * default implementation of {@link IWizardStep}. It is also a panel, which is
 * used as the view component.
 * 
 * <p>
 * And example of a custom step with a panel follows.
 * 
 * Java (defined e.g. in class x.NewUserWizard):
 * 
 * <pre>
 * private final class UserNameStep extends WizardStep
 * {
 * 	public UserNameStep()
 * 	{
 * 		super(new ResourceModel(&quot;username.title&quot;), new ResourceModel(&quot;username.summary&quot;));
 * 		add(new RequiredTextField(&quot;user.userName&quot;));
 * 		add(new RequiredTextField(&quot;user.email&quot;).add(EmailAddressPatternValidator.getInstance()));
 * 	}
 * }
 * </pre>
 * 
 * HTML (defined in e.g. file x/NewUserWizard$UserNameStep.html):
 * 
 * <pre>
 *              &lt;wicket:panel&gt;
 *               &lt;table&gt;
 *                &lt;tr&gt;
 *                 &lt;td&gt;&lt;wicket:message key=&quot;username&quot;&gt;Username&lt;/wicket:message&gt;&lt;/td&gt;
 *                 &lt;td&gt;&lt;input type=&quot;text&quot; wicket:id=&quot;user.userName&quot; /&gt;&lt;/td&gt;
 *                &lt;/tr&gt;
 *                &lt;tr&gt;
 *                 &lt;td&gt;&lt;wicket:message key=&quot;email&quot;&gt;Email Adress&lt;/wicket:message&gt;&lt;/td&gt;
 *                 &lt;td&gt;&lt;input type=&quot;text&quot; wicket:id=&quot;user.email&quot; /&gt;&lt;/td&gt;
 *                &lt;/tr&gt;
 *               &lt;/table&gt;
 *              &lt;/wicket:panel&gt;
 * </pre>
 * 
 * </p>
 * 
 * 
 * @author Eelco Hillenius
 */
public class WizardStep extends Panel implements IWizardStep
{
	/**
	 * Default header for wizards.
	 */
	private final class Header extends Panel
	{
		private static final long serialVersionUID = 1L;

		/**
		 * Construct.
		 * 
		 * @param id
		 *            The component id
		 * @param wizard
		 *            The containing wizard
		 */
		public Header(MarkupContainer parent,final String id, final IWizard wizard)
		{
			super(parent,id);
			setModel(new CompoundPropertyModel(wizard));
			add(new Label(this,"title", new AbstractReadOnlyModel()
			{
				private static final long serialVersionUID = 1L;

				public Object getObject(Component component)
				{
					return getTitle();
				}
			}).setEscapeModelStrings(false));
			add(new Label(this,"summary", new AbstractReadOnlyModel()
			{
				private static final long serialVersionUID = 1L;

				public Object getObject(Component component)
				{
					return getSummary();
				}
			}).setEscapeModelStrings(false));
		}
	}

	private static final long serialVersionUID = 1L;

	/**
	 * Marks this step as being fully configured. Only when this is
	 * <tt>true</tt> can the wizard progress.
	 */
	private boolean complete;

	/**
	 * A summary of this step, or some usage advice.
	 */
	private IModel summary;

	/**
	 * The title of this step.
	 */
	private IModel title;

	/**
	 * Construct without a title and a summary. Useful for when you provide a
	 * custom header by overiding {@link #getHeader(String, Component, Wizard)}.
	 */
	public WizardStep(MarkupContainer<?> parent)
	{
		super(parent,Wizard.VIEW_ID);
	}

	/**
	 * Creates a new step with the specified title and summary. The title and
	 * summary are displayed in the wizard title block while this step is
	 * active.
	 * 
	 * @param title
	 *            the title of this step.
	 * @param summary
	 *            a brief summary of this step or some usage guidelines.
	 */
	public WizardStep(MarkupContainer<?> parent,IModel title, IModel summary)
	{
		this(parent,title, summary, null);
	}

	/**
	 * Creates a new step with the specified title and summary. The title and
	 * summary are displayed in the wizard title block while this step is
	 * active.
	 * 
	 * @param title
	 *            the title of this step.
	 * @param summary
	 *            a brief summary of this step or some usage guidelines.
	 * @param model
	 *            Any model which is to be used for this step
	 */
	public WizardStep(MarkupContainer<?> parent,IModel title, IModel summary, IModel model)
	{
		super(parent,Wizard.VIEW_ID, model);

		this.title = title;
		this.summary = summary;
	}

	/**
	 * Creates a new step with the specified title and summary. The title and
	 * summary are displayed in the wizard title block while this step is
	 * active.
	 * 
	 * @param title
	 *            the title of this step.
	 * @param summary
	 *            a brief summary of this step or some usage guidelines.
	 */
	public WizardStep(MarkupContainer<?> parent,String title, String summary)
	{
		this(parent,title, summary, null);
	}

	/**
	 * Creates a new step with the specified title and summary. The title and
	 * summary are displayed in the wizard title block while this step is
	 * active.
	 * 
	 * @param title
	 *            the title of this step.
	 * @param summary
	 *            a brief summary of this step or some usage guidelines.
	 * @param model
	 *            Any model which is to be used for this step
	 */
	public WizardStep(MarkupContainer<?> parent,String title, String summary, IModel model)
	{
		this(parent,new Model(title), new Model(summary), null);
	}

	/**
	 * @see wicket.extensions.wizard.IWizardStep#applyState()
	 */
	public void applyState()
	{
		this.complete = true;
	}

	/**
	 * @see wicket.extensions.wizard.IWizardStep#getHeader(java.lang.String,
	 *      wicket.Component, wicket.extensions.wizard.Wizard)
	 */
	public Component getHeader(MarkupContainer parent,final String id, IWizard wizard)
	{
		return new Header(parent,id, wizard);
	}

	/**
	 * Gets the summary of this step. This will be displayed in the title of the
	 * wizard while this step is active. The summary is typically an overview of
	 * the step or some usage guidelines for the user.
	 * 
	 * @return the summary of this step.
	 */
	public String getSummary()
	{
		return (summary != null) ? (String)summary.getObject(this) : (String)null;
	}

	/**
	 * Gets the title of this step.
	 * 
	 * @return the title of this step.
	 */
	public String getTitle()
	{
		return (title != null) ? (String)title.getObject(this) : (String)null;
	}

	/**
	 * @see wicket.extensions.wizard.IWizardStep#getView(java.lang.String,
	 *      wicket.Component, wicket.extensions.wizard.Wizard)
	 */
	public Component getView(MarkupContainer parent,final String id, IWizard wizard)
	{
		return this;
	}

	/**
	 * Called to initialize the step. This method will be called when the wizard
	 * is first initialising. This implementation does nothing; override when
	 * you need to do specific work when the step initializes
	 * 
	 * @param model
	 *            the model to which the step belongs.
	 */
	public void init(IWizardModel model)
	{
	}

	/**
	 * Checks if this step is compete. This method should return true if the
	 * wizard can proceed to the next step. This property is bound and changes
	 * can be made at anytime by calling {@link #setComplete(boolean)} .
	 * 
	 * @return <tt>true</tt> if the wizard can proceed from this step,
	 *         <tt>false</tt> otherwise.
	 * @see #setComplete
	 */
	public final boolean isComplete()
	{
		return complete;
	}

	/**
	 * Marks this step as compete. The wizard will not be able to proceed from
	 * this step until this property is configured to <tt>true</tt>.
	 * 
	 * @param complete
	 *            <tt>true</tt> to allow the wizard to proceed, <tt>false</tt>
	 *            otherwise.
	 * @see #isComplete
	 */
	public final void setComplete(boolean complete)
	{
		this.complete = complete;
	}

	/**
	 * Sets summary.
	 * 
	 * @param summary
	 *            summary
	 */
	public final void setSummaryModel(IModel summary)
	{
		this.summary = summary;
	}

	/**
	 * Sets title.
	 * 
	 * @param title
	 *            title
	 */
	public final void setTitleModel(IModel title)
	{
		this.title = title;
	}
}
