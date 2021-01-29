package fi.dy.masa.minihud;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.config.JsonModConfig;
import fi.dy.masa.malilib.event.InitializationHandler;
import fi.dy.masa.malilib.event.dispatch.ClientWorldChangeEventDispatcher;
import fi.dy.masa.malilib.event.dispatch.InputDispatcher;
import fi.dy.masa.malilib.event.dispatch.KeyBindManager;
import fi.dy.masa.malilib.event.dispatch.RenderEventDispatcher;
import fi.dy.masa.malilib.event.dispatch.TickEventDispatcher;
import fi.dy.masa.malilib.gui.config.ConfigSearchInfo;
import fi.dy.masa.malilib.gui.config.ConfigTabRegistry;
import fi.dy.masa.malilib.gui.config.ConfigWidgetRegistry;
import fi.dy.masa.minihud.config.Configs;
import fi.dy.masa.minihud.config.InfoLine;
import fi.dy.masa.minihud.config.RendererToggle;
import fi.dy.masa.minihud.config.StructureToggle;
import fi.dy.masa.minihud.event.ClientTickHandler;
import fi.dy.masa.minihud.event.ClientWorldChangeHandler;
import fi.dy.masa.minihud.event.InputHandler;
import fi.dy.masa.minihud.event.RenderHandler;
import fi.dy.masa.minihud.gui.ConfigScreen;
import fi.dy.masa.minihud.gui.widget.InfoLineConfigWidget;
import fi.dy.masa.minihud.gui.widget.RendererToggleConfigWidget;
import fi.dy.masa.minihud.gui.widget.StructureToggleConfigWidget;
import fi.dy.masa.minihud.hotkeys.KeyCallbacks;

public class InitHandler implements InitializationHandler
{
    @Override
    public void registerModHandlers()
    {
        ConfigManager.INSTANCE.registerConfigHandler(new JsonModConfig(Reference.MOD_ID, Reference.MOD_NAME, Configs.CATEGORIES, 1));
        ConfigTabRegistry.INSTANCE.registerConfigTabProvider(Reference.MOD_ID, ConfigScreen::getConfigTabs);

        ConfigWidgetRegistry.INSTANCE.registerWidgetFactory(InfoLine.class, InfoLineConfigWidget::new);
        ConfigWidgetRegistry.INSTANCE.registerWidgetFactory(RendererToggle.class, RendererToggleConfigWidget::new);
        ConfigWidgetRegistry.INSTANCE.registerWidgetFactory(StructureToggle.class, StructureToggleConfigWidget::new);

        ConfigWidgetRegistry.INSTANCE.registerConfigSearchInfo(InfoLine.class, new ConfigSearchInfo<InfoLine>(true, true).setBooleanConfigGetter(InfoLine::getBooleanConfig).setKeyBindGetter(InfoLine::getKeyBind));
        ConfigWidgetRegistry.INSTANCE.registerConfigSearchInfo(RendererToggle.class, new ConfigSearchInfo<RendererToggle>(true, true).setBooleanConfigGetter(RendererToggle::getBooleanConfig).setKeyBindGetter(RendererToggle::getKeyBind));
        ConfigWidgetRegistry.INSTANCE.registerConfigSearchInfo(StructureToggle.class, new ConfigSearchInfo<StructureToggle>(true, true).setBooleanConfigGetter(StructureToggle::getBooleanConfig).setKeyBindGetter(StructureToggle::getKeyBind));

        KeyBindManager.INSTANCE.registerKeyBindProvider(InputHandler.getInstance());
        InputDispatcher.INSTANCE.registerMouseInputHandler(InputHandler.getInstance());

        RenderHandler renderer = RenderHandler.getInstance();
        RenderEventDispatcher.INSTANCE.registerGameOverlayRenderer(renderer);
        RenderEventDispatcher.INSTANCE.registerTooltipPostRenderer(renderer);
        RenderEventDispatcher.INSTANCE.registerWorldPostRenderer(renderer);

        ClientWorldChangeEventDispatcher.INSTANCE.registerClientWorldChangeHandler(new ClientWorldChangeHandler());

        TickEventDispatcher.INSTANCE.registerClientTickHandler(new ClientTickHandler());

        KeyCallbacks.init();
    }
}
