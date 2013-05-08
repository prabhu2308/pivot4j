/*
 * ====================================================================
 * This software is subject to the terms of the Common Public License
 * Agreement, available at the following URL:
 *   http://www.opensource.org/licenses/cpl.html .
 * You must accept the terms of that agreement to use this software.
 * ====================================================================
 */
package com.eyeq.pivot4j.ui.condition;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.configuration.HierarchicalConfiguration;

import com.eyeq.pivot4j.ui.AbstractRenderTest;
import com.eyeq.pivot4j.ui.RenderContext;

public abstract class AbstractConditionTest extends AbstractRenderTest {

	protected static ConditionFactory conditionFactory = new DefaultConditionFactory() {

		@Override
		public List<String> getAvailableConditions() {
			List<String> conditions = super.getAvailableConditions();
			conditions.add("TRUE");
			conditions.add("FALSE");

			return conditions;
		}

		@Override
		public Condition createCondition(String name) {
			if (name.equals("TRUE")) {
				return TestCondition.TRUE;
			} else if (name.equals("FALSE")) {
				return TestCondition.FALSE;
			}

			return super.createCondition(name);
		}
	};

	static class TestCondition extends AbstractCondition {

		private boolean result;

		static Condition TRUE = new TestCondition(true);

		static Condition FALSE = new TestCondition(false);

		private TestCondition(boolean result) {
			super(conditionFactory);

			this.result = result;
		}

		@Override
		public String getName() {
			return result ? "TRUE" : "FALSE";
		}

		@Override
		public boolean matches(RenderContext context) {
			return result;
		}

		@Override
		public Serializable saveState() {
			return result;
		}

		@Override
		public void restoreState(Serializable state) {
			this.result = (Boolean) state;
		}

		@Override
		public void saveSettings(HierarchicalConfiguration configuration) {
			super.saveSettings(configuration);

			configuration.setProperty("[@result]", result);
		}

		@Override
		public void restoreSettings(HierarchicalConfiguration configuration) {
			this.result = configuration.getBoolean("[@result]");
		}
	}
}
