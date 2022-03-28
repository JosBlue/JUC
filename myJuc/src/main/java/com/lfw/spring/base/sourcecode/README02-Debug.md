spring源码调试
Spring最核心的部分：AbstractApplicationContext-refresh()
其中包含了13个方法，都能弄明白了，那么就明白spring源码启动了

1、prepareRefresh();
方法内部详情：
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/spring-prepareRefresh.png)

// 设置标志位
this.closed.set(false);
this.active.set(true);

// 初始化属性资源，没有具体实现，用于子类扩展
initPropertySources();

// 获取环境信息，验证需要相关信息
getEnvironment().validateRequiredProperties();

附：子类在实现时，对应父类也会被实现

spring中为如图所示：
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/spring01.png)

但是springBoot中只有
this.earlyApplicationEvents = new LinkedHashSet<>();

图示spring中的作用：准备监听器的集合
// 准备监听器事件的集合对象，在springBoot中，图示的监听器肯定是有值的
this.earlyApplicationEvents = new LinkedHashSet<>();

总结：prepareRefresh()方法的作用如下：
（1）设置spring容器的启动时间
（2）设置spring容器的开启和关闭的标志位信息
（3）初始化属性资源信息
（4）获取环境相关信息，并进行验证
（5）准备相关监听器和监听事件的集合信息，默认为空的集合


2、ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
初始化并创建Bean工厂

BeanFactory有三个非常重要的子类：HierarchicalBeanFactory、ListableBeanFactory与ConfigurableBeanFactory
HierarchicalBeanFactory：层级/继承Bean工厂接口。
注释翻译：由bean工厂实现的子接口可以作为一部分等级制度的。这里，就涉及到父子容器的概念了


附：父子容器
如果是Spring容器的话，只有单一容器
但是如果是一个web项目，引入了springMVC，有springMVC容器的话，就会存在父子容器的概念
在父子容器中，每次在查找Bean信息时，会先在自己的容器中找，找不到，就去父容器中找

ListableBeanFactory：能够枚举Spring容器中所有的Bean对象 

ConfigurableBeanFactory：提供配置Factory的各种方法

总结而言，这三个接口是非常多子类接口的父接口，他们三个可以满足大部分的诉求，枚举所有的Bean对象信息，对Bean工厂进行操作，同时，还能够具有父子继承关系
 
DefaultListableBeanFactory的父类就实现了上诉三个接口，这个类是在spring中使用非常广泛，非常多的类

回到创建这一步来：
ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

具体实现：
/**
 * Tell the subclass to refresh the internal bean factory.
 * @return the fresh BeanFactory instance
 * @see #refreshBeanFactory()
 * @see #getBeanFactory()
 */
protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
    // 刷新BeanFactory，若Bean工厂存在就销毁，然后重新创建Bean工厂；
    refreshBeanFactory();
    return getBeanFactory();
}

refreshBeanFactory();具体实现
/**
	 * This implementation performs an actual refresh of this context's underlying
	 * bean factory, shutting down the previous bean factory (if any) and
	 * initializing a fresh bean factory for the next phase of the context's lifecycle.
	 */
	@Override
	protected final void refreshBeanFactory() throws BeansException {
	// 刷新BeanFactory，若Bean工厂存在就销毁关闭
		if (hasBeanFactory()) {
			destroyBeans();
			closeBeanFactory();
		}
		try {
	        // 重新创建Bean工厂；保证拿到的都是新的Bean工厂，此时，刚创建的Bean工厂，属性都是默认值
			DefaultListableBeanFactory beanFactory = createBeanFactory();
			// 设置序列化ID
			beanFactory.setSerializationId(getId());
			// TODO 了解这两个属性值
			// 设置一些属性值，allowBeanDefinitionOverriding主要设置一些Bean的定义信息是否允许被覆盖，allowCircularReferences循环依赖，后续会讲到
			customizeBeanFactory(beanFactory);
			// 加载Bean的定义信息，重载了很多方法，每个方法传入值是不一样的，因为我们编写的xml文件可能不止一个
			loadBeanDefinitions(beanFactory);
			synchronized (this.beanFactoryMonitor) {
				this.beanFactory = beanFactory;
			}
		}
		catch (IOException ex) {
			throw new ApplicationContextException("I/O error parsing bean definition source for " + getDisplayName(), ex);
		}
	}

总结：
obtainFreshBeanFactory()作用：
（1）创建容器对象:DefaultListableBeanFactory
（2）加载xml配置文件的属性值到当前工厂中，最重要的就是BeanDefinition



3、prepareBeanFactory(beanFactory);
初始化Bean工厂，或者说给当前的Bean工厂设置初始值

/**
 * Configure the factory's standard context characteristics,
 * such as the context's ClassLoader and post-processors.
 * @param beanFactory the BeanFactory to configure
 */
protected void prepareBeanFactory(ConfigurableListableBeanFactory beanFactory) {
    // Tell the internal bean factory to use the context's class loader etc.
    beanFactory.setBeanClassLoader(getClassLoader());
    // 其中包含设置SPEL解析
    beanFactory.setBeanExpressionResolver(new StandardBeanExpressionResolver(beanFactory.getBeanClassLoader()));
    beanFactory.addPropertyEditorRegistrar(new ResourceEditorRegistrar(this, getEnvironment()));

    // 设置忽略aware接口，忽略这些接口的实现
    beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
    beanFactory.ignoreDependencyInterface(EnvironmentAware.class);
    beanFactory.ignoreDependencyInterface(EmbeddedValueResolverAware.class);
    beanFactory.ignoreDependencyInterface(ResourceLoaderAware.class);
    beanFactory.ignoreDependencyInterface(ApplicationEventPublisherAware.class);
    beanFactory.ignoreDependencyInterface(MessageSourceAware.class);
    beanFactory.ignoreDependencyInterface(ApplicationContextAware.class);

    // BeanFactory interface not registered as resolvable type in a plain factory.
    // MessageSource registered (and found for autowiring) as a bean.
    // 设置一些依赖
    beanFactory.registerResolvableDependency(BeanFactory.class, beanFactory);
    beanFactory.registerResolvableDependency(ResourceLoader.class, this);
    beanFactory.registerResolvableDependency(ApplicationEventPublisher.class, this);
    beanFactory.registerResolvableDependency(ApplicationContext.class, this);

    // Register early post-processor for detecting inner beans as ApplicationListeners.
    beanFactory.addBeanPostProcessor(new ApplicationListenerDetector(this));

    // Detect a LoadTimeWeaver and prepare for weaving, if found.
    if (beanFactory.containsBean(LOAD_TIME_WEAVER_BEAN_NAME)) {
        beanFactory.addBeanPostProcessor(new LoadTimeWeaverAwareProcessor(beanFactory));
        // Set a temporary ClassLoader for type matching.
        beanFactory.setTempClassLoader(new ContextTypeMatchClassLoader(beanFactory.getBeanClassLoader()));
    }

    // Register default environment beans.
    if (!beanFactory.containsLocalBean(ENVIRONMENT_BEAN_NAME)) {
        beanFactory.registerSingleton(ENVIRONMENT_BEAN_NAME, getEnvironment());
    }
    if (!beanFactory.containsLocalBean(SYSTEM_PROPERTIES_BEAN_NAME)) {
        beanFactory.registerSingleton(SYSTEM_PROPERTIES_BEAN_NAME, getEnvironment().getSystemProperties());
    }
    if (!beanFactory.containsLocalBean(SYSTEM_ENVIRONMENT_BEAN_NAME)) {
        beanFactory.registerSingleton(SYSTEM_ENVIRONMENT_BEAN_NAME, getEnvironment().getSystemEnvironment());
    }   
}

所以当前方法，就是为了初始化Bean工厂


4、postProcessBeanFactory(beanFactory);
模板方法，方便做扩展操作


5、invokeBeanFactoryPostProcessors(beanFactory); 
执行Bean工厂的后置处理器（就是执行所有对BeanDefinition的增强器）


/**
 * Instantiate and invoke all registered BeanFactoryPostProcessor beans,
 * respecting explicit order if given.
 * <p>Must be called before singleton instantiation.
 * 整个这个部分的注释翻译，就是实例化并执行所有注册的BeanFactoryPostProcessor Bean,如果有顺序，就按照顺序执行
 * 必须要在单例实例化之前被调用，因为只有在实例化之前对BeanDefinition进行增强操作才有意义
 * 为什么要是单例？是因为如果是propotyscope类型，每次都会创建对象，那么在创建时，可以对其进行更改，但是单例的，在容器启动完成后，
 * 对象就已经被创建完成了，此时在对其BeanDefinition进行更改已经没有意义了
 */
protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
    PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(beanFactory, getBeanFactoryPostProcessors());

// Detect a LoadTimeWeaver and prepare for weaving, if found in the meantime
// (e.g. through an @Bean method registered by ConfigurationClassPostProcessor)
if (beanFactory.getTempClassLoader() == null && beanFactory.containsBean(LOAD_TIME_WEAVER_BEAN_NAME)) {
    beanFactory.addBeanPostProcessor(new LoadTimeWeaverAwareProcessor(beanFactory));
    beanFactory.setTempClassLoader(new ContextTypeMatchClassLoader(beanFactory.getBeanClassLoader()));
    }
}

到这里完成之后，我们后续，就需要进行实例化了，但是在实例化之前，还是需要准备一些实例化需要的东西

6、registerBeanPostProcessors(beanFactory);
作用：实例化并注册所有BeanPostProcessor，也就是将所有对Bean进行增强的Processor进行注册，也是一个准备工作

7、initMessageSource();
初始化消息资源，就是做国际化的一个操作


8、initApplicationEventMulticaster();
初始化应用事件的多播器（广播器），为了后续广播一个事件时，注册器接收到对应的广播事件，就可以执行相应的操作。也是一个准备工作


9、onRefresh();
空方法实现，留给子类做方法的扩展实现的
在springBoot中，在这个方法中，就是启动了tomcat容器

10、registerListeners();
注册监听器，也是准备工作之一


从6到10，这5个方法，除了一个做国际化处理的方法，其他的都是为做实例化而做的准备工作


11、finishBeanFactoryInitialization(beanFactory);
这个方法非常重要，也是实际工作中常用的，也是最常被问到的方法
先说他的注释意思：实例化剩下的，非懒加载的单例对象


@Override
public void preInstantiateSingletons() throws BeansException {
    if (logger.isTraceEnabled()) {
        logger.trace("Pre-instantiating singletons in " + this);
    }

// Iterate over a copy to allow for init methods which in turn register new bean definitions.
// While this may not be part of the regular factory bootstrap, it does otherwise work fine.
// 获取配置文件中所有的bean信息，转换成List，供后续循环
List<String> beanNames = new ArrayList<>(this.beanDefinitionNames);

// Trigger initialization of all non-lazy singleton beans...
for (String beanName : beanNames) {
// RootBeanDefinition这个非常重要，这个getMergedLocalBeanDefinition方法就是将当前Bean自己和其父类的定义信息合并到一起
    RootBeanDefinition bd = getMergedLocalBeanDefinition(beanName);
    if (!bd.isAbstract() && bd.isSingleton() && !bd.isLazyInit()) {
    // 判断当前这个对象是否实现了FactoryBean这个类？？？
        if (isFactoryBean(beanName)) {
            Object bean = getBean(FACTORY_BEAN_PREFIX + beanName);
            if (bean instanceof FactoryBean) {
                final FactoryBean<?> factory = (FactoryBean<?>) bean;
                boolean isEagerInit;
                if (System.getSecurityManager() != null && factory instanceof SmartFactoryBean) {
                    isEagerInit = AccessController.doPrivileged((PrivilegedAction<Boolean>)
                                    ((SmartFactoryBean<?>) factory)::isEagerInit,
                            getAccessControlContext());
                }
                else {
                    isEagerInit = (factory instanceof SmartFactoryBean &&
                            ((SmartFactoryBean<?>) factory).isEagerInit());
                }
                if (isEagerInit) {
                    getBean(beanName);
                }
            }
        }
        else {
        // 直接问获取Bean，其中的详情见下
            getBean(beanName);
        }
    }
}

// Trigger post-initialization callback for all applicable beans...
for (String beanName : beanNames) {
    Object singletonInstance = getSingleton(beanName);
    if (singletonInstance instanceof SmartInitializingSingleton) {
        final SmartInitializingSingleton smartSingleton = (SmartInitializingSingleton) singletonInstance;
        if (System.getSecurityManager() != null) {
            AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
                smartSingleton.afterSingletonsInstantiated();
                return null;
            }, getAccessControlContext());
        }
        else {
            smartSingleton.afterSingletonsInstantiated();
        }
    }
}
}

在getBean中，执行的方法就是doGetBean()

/**
 * Return an instance, which may be shared or independent, of the specified bean.
 * @param name the name of the bean to retrieve
 * @param requiredType the required type of the bean to retrieve
 * @param args arguments to use when creating a bean instance using explicit arguments
 * (only applied when creating a new instance as opposed to retrieving an existing one)
 * @param typeCheckOnly whether the instance is obtained for a type check,
 * not for actual use
 * @return an instance of the bean
 * @throws BeansException if the bean could not be created
 */
@SuppressWarnings("unchecked")
protected <T> T doGetBean(final String name, @Nullable final Class<T> requiredType,
        @Nullable final Object[] args, boolean typeCheckOnly) throws BeansException {

final String beanName = transformedBeanName(name);
Object bean;

// Eagerly check singleton cache for manually registered singletons.
Object sharedInstance = getSingleton(beanName);
if (sharedInstance != null && args == null) {
    if (logger.isTraceEnabled()) {
        if (isSingletonCurrentlyInCreation(beanName)) {
            logger.trace("Returning eagerly cached instance of singleton bean '" + beanName +
                    "' that is not fully initialized yet - a consequence of a circular reference");
        }
        else {
            logger.trace("Returning cached instance of singleton bean '" + beanName + "'");
        }
    }
    bean = getObjectForBeanInstance(sharedInstance, name, beanName, null);
}

else {
    // Fail if we're already creating this bean instance:
    // We're assumably within a circular reference.
    if (isPrototypeCurrentlyInCreation(beanName)) {
        throw new BeanCurrentlyInCreationException(beanName);
    }

    // Check if bean definition exists in this factory.
    BeanFactory parentBeanFactory = getParentBeanFactory();
    if (parentBeanFactory != null && !containsBeanDefinition(beanName)) {
        // Not found -> check parent.
        String nameToLookup = originalBeanName(name);
        if (parentBeanFactory instanceof AbstractBeanFactory) {
            return ((AbstractBeanFactory) parentBeanFactory).doGetBean(
                    nameToLookup, requiredType, args, typeCheckOnly);
        }
        else if (args != null) {
            // Delegation to parent with explicit args.
            return (T) parentBeanFactory.getBean(nameToLookup, args);
        }
        else if (requiredType != null) {
            // No args -> delegate to standard getBean method.
            return parentBeanFactory.getBean(nameToLookup, requiredType);
        }
        else {
            return (T) parentBeanFactory.getBean(nameToLookup);
        }
    }

    if (!typeCheckOnly) {
        markBeanAsCreated(beanName);
    }

    try {
        final RootBeanDefinition mbd = getMergedLocalBeanDefinition(beanName);
        checkMergedBeanDefinition(mbd, beanName, args);

        // Guarantee initialization of beans that the current bean depends on.
        String[] dependsOn = mbd.getDependsOn();
        if (dependsOn != null) {
            for (String dep : dependsOn) {
                if (isDependent(beanName, dep)) {
                    throw new BeanCreationException(mbd.getResourceDescription(), beanName,
                            "Circular depends-on relationship between '" + beanName + "' and '" + dep + "'");
                }
                registerDependentBean(dep, beanName);
                try {
                    getBean(dep);
                }
                catch (NoSuchBeanDefinitionException ex) {
                    throw new BeanCreationException(mbd.getResourceDescription(), beanName,
                            "'" + beanName + "' depends on missing bean '" + dep + "'", ex);
                }
            }
        }

        // Create bean instance.
        if (mbd.isSingleton()) {
            sharedInstance = getSingleton(beanName, () -> {
                try {
                    return createBean(beanName, mbd, args);
                }
                catch (BeansException ex) {
                    // Explicitly remove instance from singleton cache: It might have been put there
                    // eagerly by the creation process, to allow for circular reference resolution.
                    // Also remove any beans that received a temporary reference to the bean.
                    destroySingleton(beanName);
                    throw ex;
                }
            });
            bean = getObjectForBeanInstance(sharedInstance, name, beanName, mbd);
        }

        else if (mbd.isPrototype()) {
            // It's a prototype -> create a new instance.
            Object prototypeInstance = null;
            try {
                beforePrototypeCreation(beanName);
                prototypeInstance = createBean(beanName, mbd, args);
            }
            finally {
                afterPrototypeCreation(beanName);
            }
            bean = getObjectForBeanInstance(prototypeInstance, name, beanName, mbd);
        }

        else {
            String scopeName = mbd.getScope();
            final Scope scope = this.scopes.get(scopeName);
            if (scope == null) {
                throw new IllegalStateException("No Scope registered for scope name '" + scopeName + "'");
            }
            try {
                Object scopedInstance = scope.get(beanName, () -> {
                    beforePrototypeCreation(beanName);
                    try {
                        return createBean(beanName, mbd, args);
                    }
                    finally {
                        afterPrototypeCreation(beanName);
                    }
                });
                bean = getObjectForBeanInstance(scopedInstance, name, beanName, mbd);
            }
            catch (IllegalStateException ex) {
                throw new BeanCreationException(beanName,
                        "Scope '" + scopeName + "' is not active for the current thread; consider " +
                        "defining a scoped proxy for this bean if you intend to refer to it from a singleton",
                        ex);
            }
        }
    }
    catch (BeansException ex) {
        cleanupAfterBeanCreationFailure(beanName);
        throw ex;
    }
}

// Check if required type matches the type of the actual bean instance.
if (requiredType != null && !requiredType.isInstance(bean)) {
    try {
        T convertedBean = getTypeConverter().convertIfNecessary(bean, requiredType);
        if (convertedBean == null) {
            throw new BeanNotOfRequiredTypeException(name, requiredType, bean.getClass());
        }
        return convertedBean;
    }
    catch (TypeMismatchException ex) {
        if (logger.isTraceEnabled()) {
            logger.trace("Failed to convert bean '" + name + "' to required type '" +
                    ClassUtils.getQualifiedName(requiredType) + "'", ex);
        }
        throw new BeanNotOfRequiredTypeException(name, requiredType, bean.getClass());
    }
}
return (T) bean;
}











