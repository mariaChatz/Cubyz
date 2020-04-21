package io.cubyz.ui.options;

import io.cubyz.client.Cubyz;
import io.cubyz.translate.ContextualTextKey;
import io.cubyz.translate.LanguageLoader;
import io.cubyz.translate.TextKey;
import io.cubyz.ui.MenuGUI;
import io.cubyz.ui.components.Button;
import io.cubyz.utils.DiscordIntegration;
import io.jungle.Window;

public class OptionsGUI extends MenuGUI {

	private Button done = new Button();
	private Button language = new Button();
	private Button rpc = new Button();
	private Button bindings = new Button();
	private Button fog = new Button();
	
	private ContextualTextKey langKey = new ContextualTextKey("gui.cubyz.options.language", 1);
	private ContextualTextKey rpcKey = new ContextualTextKey("gui.cubyz.options.discord", 1);
	
	private String[] languages = new String[] {"en_US", "fr_FR", "ro_RO"};
	
	@Override
	public void init(long nvg) {
		done.setSize(250, 45);
		done.setText(new TextKey("gui.cubyz.options.done"));
		done.setFontSize(16f);
		
		done.setOnAction(() -> {
			Cubyz.gameUI.back();
		});
		
		bindings.setSize(250, 45);
		bindings.setText(new TextKey("gui.cubyz.options.keybindings"));
		bindings.setFontSize(16f);
		
		bindings.setOnAction(() -> {
			Cubyz.gameUI.setMenu(new KeybindingsGUI());
		});
		
		language.setSize(250, 45);
		langKey.setArgument(0, Cubyz.lang.translate(new TextKey("lang.name")));
		language.setText(langKey);
		language.setFontSize(16f);
		
		language.setOnAction(() -> {
			int index = -1;
			for (int i = 0; i < languages.length; i++) {
				if (languages[i].equals(Cubyz.lang.getLocale())) {
					index = i;
					break;
				}
			}
			index++;
			if (index >= languages.length) index = 0;
			Cubyz.lang = LanguageLoader.load(languages[index]);
			langKey.setArgument(0, Cubyz.lang.translate(new TextKey("lang.name")));
		});
		
		rpc.setSize(250, 45);
		rpcKey.setArgument(0, "");
		rpc.setText(rpcKey);
		rpc.setFontSize(16f);
		
		rpc.setOnAction(() -> {
			if (DiscordIntegration.isEnabled()) {
				DiscordIntegration.closeRPC();
			} else {
				DiscordIntegration.startRPC();
			}
		});
		
		if (Cubyz.fogCoefficient == 0f) {
			fog.setText(new TextKey("gui.cubyz.options.fog.off"));
		} else if (Cubyz.fogCoefficient <= 5f) {
			fog.setText(new TextKey("gui.cubyz.options.fog.near"));
		} if (Cubyz.fogCoefficient > 5f && Cubyz.fogCoefficient < 15f) {
			fog.setText(new TextKey("gui.cubyz.options.fog.med"));
		} else {
			fog.setText(new TextKey("gui.cubyz.options.fog.far"));
		}
		fog.setOnAction(() -> {
			if (Cubyz.fogCoefficient == 0f) { // off
				Cubyz.fogCoefficient = 5f;
				fog.setText(new TextKey("gui.cubyz.options.fog.near"));
			} else if (Cubyz.fogCoefficient <= 5f) { // near
				Cubyz.fogCoefficient = 10f;
				fog.setText(new TextKey("gui.cubyz.options.fog.med"));
			} else if (Cubyz.fogCoefficient > 5f && Cubyz.fogCoefficient < 15f) { // medium
				Cubyz.fogCoefficient = 15f;
				fog.setText(new TextKey("gui.cubyz.options.fog.far"));
			} else { // far
				Cubyz.fogCoefficient = 0f;
				fog.setText(new TextKey("gui.cubyz.options.fog.off"));
			}
		});
		fog.setSize(250, 45);
		fog.setFontSize(16f);
		
	}

	@Override
	public void render(long nvg, Window win) {
		done.setPosition(win.getWidth() / 2 - 125, win.getHeight() - 75);
		language.setPosition(win.getWidth() / 2 - 125, 75);
		rpc.setPosition(win.getWidth() / 2 - 125, 150);
		bindings.setPosition(win.getWidth() / 2 - 125, 215);
		fog.setPosition(win.getWidth() / 2 - 125, 280);
		rpcKey.setArgument(0, DiscordIntegration.isEnabled() ? Cubyz.lang.translate(new TextKey("gui.cubyz.general.on")) : Cubyz.lang.translate(new TextKey("gui.cubyz.general.off")));
		
		done.render(nvg, win);
		language.render(nvg, win);
		rpc.render(nvg, win);
		bindings.render(nvg, win);
		fog.render(nvg, win);
	}
	
	@Override
	public boolean ungrabsMouse() {
		return true;
	}

	@Override
	public boolean doesPauseGame() {
		return true;
	}

}
